package com.example.promonade.service;

import com.example.promonade.dto.request.promotiondtos.CriteriaRequest;
import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.enums.promotionEnums.PromotionType;
import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.exceptions.promotionExceptions.PromotionIncompleteException;
import com.example.promonade.exceptions.promotionExceptions.PromotionNotFoundException;
import com.example.promonade.exceptions.userExceptions.TeamNotAuthorisedException;
import com.example.promonade.exceptions.userExceptions.UserNotFoundException;
import com.example.promonade.models.Criteria;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.User;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import com.example.promonade.service.transformers.PromotionTransformer;
import com.example.promonade.service.utils.GeneralUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PromotionsService {
    private final PromotionRepository promotionRepository;
    private final GeneralUtils generalUtils;

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
        Promotion promotion1 = promotionRepository.save(promotion);
        String message = "Promotion Not approved";
        boolean success = false;
        if(promotion1.getApproved()){
            message = String.format("Promotion %s with id %d is successfully approved", promotion1.getName(), promotion1.getId());
            success = true;
        }
        System.out.println(promotion1);
        return new UpdationResponse(message, success);
    }
}
