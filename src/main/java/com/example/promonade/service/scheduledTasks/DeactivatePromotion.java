package com.example.promonade.service.scheduledTasks;


import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.PromotionRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class DeactivatePromotion extends PromotionAct{

    @Autowired
    private PromotionRepository promotionRepository;

    public DeactivatePromotion(String cronExpression, Promotion promotion, PromotionRepository promotionRepository){
        super.setCronExpression(cronExpression);
        super.setActionType("Deactivate Promotion");
        super.setPromotion(promotion);
        this.promotionRepository = promotionRepository;
    }

    public void perform(){
        Promotion promotion = this.getPromotion();
        promotion.setActive(false);
        this.promotionRepository.save(promotion);
    }
}
