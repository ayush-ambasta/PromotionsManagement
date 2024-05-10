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
public class ActivatePromotion extends ScheduleTask implements Runnable{

    private PromotionRepository promotionRepository;

    public ActivatePromotion(PromotionScheduleAction action, Promotion promotion, PromotionRepository promotionRepository){
        super(action, promotion);
        this.promotionRepository = promotionRepository;
    }

    @Override
    public void run() {
        super.setActionType(PromotionScheduleAction.ACTIVATEPROMOTION);
        System.out.println("Running Action: " + super.getActionType().toString());
        Promotion promotion = super.getPromotion();
        promotion.setActive(true);
        promotionRepository.save(promotion);
    }
}
