package com.example.promonade.scheduler;


import com.example.promonade.service.scheduledTasks.PromotionAct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class DeactivatePromotionBean implements Runnable{

    private PromotionAct promotionAct;

    @Override
    public void run() {
        System.out.println("Running Action: " + promotionAct.getActionType());
        promotionAct.perform();
    }
}