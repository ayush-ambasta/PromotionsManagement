package com.example.promonade.scheduler;

import com.example.promonade.enums.promotionEnums.PromotionScheduleAction;
import com.example.promonade.models.Promotion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleTask {

    private PromotionScheduleAction actionType;
    private Promotion promotion;

    public ScheduleTask(PromotionScheduleAction action, Promotion promotion){
        this.actionType = action;
        this.promotion = promotion;
    }

}
