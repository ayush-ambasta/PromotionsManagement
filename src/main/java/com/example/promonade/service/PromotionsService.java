package com.example.promonade.service;

import com.example.promonade.dto.request.promotiondtos.CriteriaRequest;
import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.enums.promotionEnums.PromotionScheduleAction;
import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.exceptions.promotionExceptions.PromotionIncompleteException;
import com.example.promonade.exceptions.promotionExceptions.PromotionNotFoundException;
import com.example.promonade.exceptions.userExceptions.TeamNotAuthorisedException;
import com.example.promonade.models.Criteria;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.User;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.scheduler.ActivatePromotion;
import com.example.promonade.scheduler.DeactivatePromotion;
import com.example.promonade.scheduler.ScheduleTask;
import com.example.promonade.service.transformers.PromotionTransformer;
import com.example.promonade.service.utils.GeneralUtils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
@AllArgsConstructor
public class PromotionsService {
    private final PromotionRepository promotionRepository;
    private final GeneralUtils generalUtils;
    private final TaskScheduler taskScheduler;

    Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public Promotion createPromotion(PromotionRequest promotionRequest, String authToken){
        if(promotionRequest.getCategory()==null){
            throw new PromotionIncompleteException("Promotion category cannot be empty!");
        }
        if(promotionRequest.getName()==null){
            throw new PromotionIncompleteException("Promotion name cannot be empty!");
        }
        if(promotionRequest.getCriteria()==null){
            throw new PromotionIncompleteException("Promotion criteria cannot be empty!");
        }
        if(promotionRequest.getValidFrom()==null || promotionRequest.getValidTill()==null){
            throw new PromotionIncompleteException("Promotion validity cannot be empty!");
        }
        if(promotionRequest.getPromotionType()==null){
            throw new PromotionIncompleteException("Promotion type cannot be empty!");
        }

        User createdBy = generalUtils.getUserFromAuthToken(authToken);
        String userTeam = createdBy.getTeam().toString();
        String promoCat = promotionRequest.getCategory().toString();

        //when team does not match the promo type
        if(!userTeam.substring(0, userTeam.length()-11).equals(promoCat)){
            throw new TeamNotAuthorisedException(String.format("The team %s cannot create %s promotion type!", userTeam, promoCat));
        }

        Promotion promotion = PromotionTransformer.promotionRequestToPromotion(promotionRequest, createdBy);

        promotionRepository.save(promotion);

        return promotion;
    }

    public UpdationResponse deletePromotion(int id, String authToken) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        Promotion promotion = promotionOptional.get();
        User createdBy = generalUtils.getUserFromAuthToken(authToken);
        String userTeam = createdBy.getTeam().toString();
        String promoCat = promotion.getCategory().toString();

        //when team does not match the promo type
        if(!userTeam.substring(0, userTeam.length()-11).equals(promoCat)){
            throw new TeamNotAuthorisedException(String.format("The team %s cannot delete %s promotion type!", userTeam, promoCat));
        }
        promotionRepository.delete(promotion);
        String message = String.format("Promotion %d with the name %s successfully deleted!", promotion.getId(), promotion.getName());
        return new UpdationResponse(message, true);
    }

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion getPromotion(int id){
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        return promotionOptional.get();
    }

    public Promotion editPromotion(int id, PromotionRequest promotionRequest) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException(String.format("Promotion not found for id %d", id));
        }
        Promotion promotion = promotionOptional.get();
        Criteria criteria = promotion.getCriteria();

        CriteriaRequest criteriaRequest = promotionRequest.getCriteria();

        promotion.setPromotionType(promotionRequest.getPromotionType());
        promotion.setCategory(promotionRequest.getCategory());
        promotion.setName(promotionRequest.getName());
        promotion.setValidFrom(promotionRequest.getValidFrom());
        promotion.setValidTill(promotionRequest.getValidTill());

        criteria.setAgeCategory(criteriaRequest.getAgeCategory());
        criteria.setGender(criteriaRequest.getGender());
        criteria.setProductType(criteriaRequest.getProductType());
        criteriaRequest.setMaritalStatus(criteriaRequest.getMaritalStatus());

        promotion.setCriteria(criteria);

        return promotionRepository.save(promotion);
    }

    public List<Promotion> getNonApprovedPromotionsForUserTeam(String authToken) {
        System.out.println(authToken);
        User user = generalUtils.getUserFromAuthToken(authToken);
        Team team = user.getTeam();
        String promoCat = team.toString().substring(0, team.toString().length()-11);
        PromotionCategory category = PromotionCategory.valueOf(promoCat);
        return promotionRepository.findNonApprovedPromotionsByCategory(category);
    }

    public List<Promotion> getApprovedPromotionsForUserTeam(String authToken) {
        User user = generalUtils.getUserFromAuthToken(authToken);
        Team team = user.getTeam();
        String promoCat = team.toString().substring(0, team.toString().length()-11);
        PromotionCategory category = PromotionCategory.valueOf(promoCat);
        return promotionRepository.findApprovedPromotionsByCategory(category);
    }

    public UpdationResponse approvePromotion(int id, String headerAuth) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        Promotion promotion = promotionOptional.get();
        User user = generalUtils.getUserFromAuthToken(headerAuth);

        String userTeam = user.getTeam().toString();
        String promoCat = promotion.getCategory().toString();

        //when team does not match the promo type
        if(!userTeam.substring(0, userTeam.length()-11).equals(promoCat)){
            throw new TeamNotAuthorisedException(String.format("The Owner of team %s cannot approve %s promotion type!", userTeam, promoCat));
        }

        promotion.setApproved(true);

        schedulePromotion(promotion, PromotionScheduleAction.ACTIVATEPROMOTION);
        schedulePromotion(promotion, PromotionScheduleAction.DEACTIVATEPROMOTION);

        Promotion promotion1 = promotionRepository.save(promotion);

        String message = String.format("Promotion %s with id %d is successfully approved", promotion1.getName(), promotion1.getId());
        boolean success = true;
        return new UpdationResponse(message, success);
    }

    public UpdationResponse disapprovePromotion(int id, String headerAuth) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        Promotion promotion = promotionOptional.get();
        User user = generalUtils.getUserFromAuthToken(headerAuth);

        String userTeam = user.getTeam().toString();
        String promoCat = promotion.getCategory().toString();

        //when team does not match the promo type
        if(!userTeam.substring(0, userTeam.length()-11).equals(promoCat)){
            throw new TeamNotAuthorisedException(String.format("The Owner of team %s cannot disapprove %s promotion type!", userTeam, promoCat));
        }

        promotion.setApproved(false);
        promotion.setDecline(true);
        Promotion promotion1 = promotionRepository.save(promotion);

        String message = String.format("Promotion %s with id %d is successfully disapproved", promotion1.getName(), promotion1.getId());
        boolean success = true;
        return new UpdationResponse(message, success);
    }

    public void schedulePromotion(Promotion promotion, PromotionScheduleAction action){
        ScheduledFuture<?> scheduledTask;
        int jobId = action.equals(PromotionScheduleAction.ACTIVATEPROMOTION)? promotion.getId()*2: (promotion.getId()*2)+1;
        Date date = action.equals(PromotionScheduleAction.ACTIVATEPROMOTION)? promotion.getValidFrom(): promotion.getValidTill();
        String cronExpression = generalUtils.convertToCronExpression(date);

        System.out.println("Scheduling task with job id: " + jobId + " and cron expression: " + cronExpression + " and promotionStart: "+ action.toString());

        ScheduleTask scheduleTask = action.equals(PromotionScheduleAction.ACTIVATEPROMOTION)?
                new ActivatePromotion(action, promotion, promotionRepository):
                new DeactivatePromotion(action, promotion, promotionRepository);

        scheduledTask = taskScheduler.schedule((Runnable) scheduleTask, new CronTrigger(cronExpression, TimeZone.getTimeZone("Asia/Kolkata")));

        jobsMap.put(jobId, scheduledTask);
    }

    public void removeScheduledPromotion(int jobId){
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    public UpdationResponse deactivatePromotion(int id, String headerAuth) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        Promotion promotion = promotionOptional.get();
        User user = generalUtils.getUserFromAuthToken(headerAuth);

        String userTeam = user.getTeam().toString();
        String promoCat = promotion.getCategory().toString();

        //when team does not match the promo type
        if(!userTeam.substring(0, userTeam.length()-11).equals(promoCat)){
            throw new TeamNotAuthorisedException(String.format("The Owner of team %s cannot deactivate %s promotion type!", userTeam, promoCat));
        }

        promotion.setActive(false);
        Promotion promotion1 = promotionRepository.save(promotion);

        String message = String.format("Promotion %s with id %d is successfully deactivated", promotion1.getName(), promotion1.getId());
        boolean success = true;
        return new UpdationResponse(message, success);
    }

    public UpdationResponse deleteAllPromotions() {
        promotionRepository.deleteAll();
        return new UpdationResponse("All promotions deleted successfully", true);
    }
}
