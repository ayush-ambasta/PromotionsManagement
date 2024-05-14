package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.customerdtos.DemographyRequest;
import com.example.promonade.models.Demographics;

public class DemographyTransformer {

    public static Demographics demographyRequestToDemographics(DemographyRequest demographyRequest)
    {
        return Demographics.builder()
                .Age(demographyRequest.getAge())
                .ageCAT(demographyRequest.getAgeCAT())
                .gender(demographyRequest.getGender())
                .maritalStatus(demographyRequest.getMaritalStatus())
                .build();
    }
}
