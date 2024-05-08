package com.example.promonade.service.scheduledTasks;

import com.example.promonade.models.Promotion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class PromotionAct {
    private String cronExpression;
    private String actionType;
    private Promotion promotion;

    public abstract void perform();
}
