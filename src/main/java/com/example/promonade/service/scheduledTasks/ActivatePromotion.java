package com.example.promonade.service.scheduledTasks;

import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.PromotionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
public class ActivatePromotion extends PromotionAct {

    @Autowired
    private PromotionRepository promotionRepository;


    public ActivatePromotion(String cronExpression, Promotion promotion, PromotionRepository promotionRepository){
        super.setCronExpression(cronExpression);
        super.setActionType("Activate Promotion");
        super.setPromotion(promotion);
        this.promotionRepository = promotionRepository;
    }

    public void perform(){
        Promotion promotion = this.getPromotion();
        promotion.setActive(true);
        this.promotionRepository.save(promotion);
    }
}
