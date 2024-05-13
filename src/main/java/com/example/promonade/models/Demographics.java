package com.example.promonade.models;

import com.example.promonade.enums.customerEnums.AgeCategory;
import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.customerEnums.MaritalStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Demographics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Enumerated(EnumType.STRING)
    AgeCategory ageCAT;

    int Age;

    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    Gender gender;
}
