package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.customerdtos.BehaviourRequest;
import com.example.promonade.models.Behaviour;

public class BehaviourTransformer {

    public static Behaviour behaviourRequestToBehaviour(BehaviourRequest behaviourRequest)
    {
        return Behaviour.builder()
                .cartAbandonmentRate(behaviourRequest.getCartAbandonmentRate())
                .expenditure(behaviourRequest.getExpenditure())
                .lastLoginTime(behaviourRequest.getLastLoginTime())
                .LoginFrequency(behaviourRequest.getLoginFrequency())
                .purchaseFrequency(behaviourRequest.getPurchaseFrequency())
                .build();
    }


}
