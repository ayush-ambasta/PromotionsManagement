package com.example.promonade.scheduler;


import com.example.promonade.dto.request.EmailDetails;
import com.example.promonade.enums.promotionEnums.PromotionScheduleAction;
import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.service.utils.EmailService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class DeactivatePromotion extends ScheduleTask implements Runnable {

    private PromotionRepository promotionRepository;
    private EmailService emailService;

    public DeactivatePromotion(PromotionScheduleAction action, Promotion promotion, PromotionRepository promotionRepository, EmailService emailService){
        super(action, promotion);
        this.promotionRepository = promotionRepository;
        this.emailService = emailService;
    }

    public String formulatePromotionEndMessage(String receiver, String promotionName){
        return String.format("Hi %s,\nYour Promotion %s has completed its duration", receiver, promotionName);
    }

    @Override
    public void run() {
        super.setActionType(PromotionScheduleAction.DEACTIVATEPROMOTION);
        System.out.println("Running Action: " + super.getActionType().toString());
        Promotion promotion = super.getPromotion();
        promotion.setActive(false);
        promotionRepository.save(promotion);

        String emailBody = formulatePromotionEndMessage(promotion.getCreatedBy().getName(), promotion.getName());
        EmailDetails details = new EmailDetails(promotion.getCreatedBy().getEmail(), "Promonade Login Credentials", emailBody);
        String status = emailService.sendMail(details);
        System.out.println(status);
    }
}