package com.example.promonade.models;

import com.example.promonade.enums.customerEnums.AgeCategory;
import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.customerEnums.MaritalStatus;
import com.example.promonade.enums.productEnums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    AgeCategory ageCategory;

    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    ProductType productType;
}
