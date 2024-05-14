package com.example.promonade.dto.request.customerdtos;

import com.example.promonade.enums.customerEnums.AgeCategory;
import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.customerEnums.MaritalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DemographyRequest {

    Long customerId;

    AgeCategory ageCAT;

    int Age;

    MaritalStatus maritalStatus;

    Gender gender;
}
