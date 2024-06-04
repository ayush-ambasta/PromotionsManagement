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

import java.util.Date;


@Getter
@Setter
public class ActivatePromotion extends ScheduleTask implements Runnable{

    private PromotionRepository promotionRepository;
    private EmailService emailService;

    public ActivatePromotion(PromotionScheduleAction action, Promotion promotion, PromotionRepository promotionRepository, EmailService emailService){
        super(action, promotion);
        this.promotionRepository = promotionRepository;
        this.emailService = emailService;
    }

    public String formulatePromotionStartMessage(String receiver, String promotionName, Date validTill){
        return String.format("Hi %s,\nYour Promotion %s is now Live until %s", receiver, promotionName, validTill);
    }

    @Override
    public void run() {
        super.setActionType(PromotionScheduleAction.ACTIVATEPROMOTION);
        System.out.println("Running Action: " + super.getActionType().toString());
        Promotion promotion = super.getPromotion();
        promotion.setActive(true);
        promotionRepository.save(promotion);

        String emailBody = formulatePromotionStartMessage(promotion.getCreatedBy().getName(), promotion.getName(), promotion.getValidTill());
        EmailDetails details = new EmailDetails(promotion.getCreatedBy().getEmail(), "Promonade Login Credentials", emailBody);
        String status = emailService.sendMail(details);
        System.out.println(status);
    }
}
