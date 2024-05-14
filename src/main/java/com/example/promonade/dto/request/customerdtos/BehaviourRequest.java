package com.example.promonade.dto.request.customerdtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BehaviourRequest {

    //this will take customer id too to save behaviour for respective customers
    Long customerId;

    double cartAbandonmentRate;

    int LoginFrequency;

    int purchaseFrequency;

    Date lastLoginTime;

    double expenditure;
}
