package com.example.promonade.scheduler;


import com.example.promonade.enums.promotionEnums.PromotionScheduleAction;
import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.PromotionRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class DeactivatePromotion extends ScheduleTask implements Runnable {

    private PromotionRepository promotionRepository;

    public DeactivatePromotion(PromotionScheduleAction action, Promotion promotion, PromotionRepository promotionRepository){
        super(action, promotion);
        this.promotionRepository = promotionRepository;
    }

    @Override
    public void run() {
        super.setActionType(PromotionScheduleAction.DEACTIVATEPROMOTION);
        System.out.println("Running Action: " + super.getActionType().toString());
        Promotion promotion = super.getPromotion();
        promotion.setActive(false);
        promotionRepository.save(promotion);
    }
}