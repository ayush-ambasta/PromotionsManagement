package com.example.promonade.dto.request.promotiondtos;

import com.example.promonade.enums.customerEnums.AgeCategory;
import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.customerEnums.MaritalStatus;
import com.example.promonade.enums.productEnums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CriteriaRequest {
    AgeCategory ageCategory;

    MaritalStatus maritalStatus;

    Gender gender;

    ProductType productType;
}
