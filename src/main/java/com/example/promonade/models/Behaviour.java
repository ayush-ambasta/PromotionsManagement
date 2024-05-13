package com.example.promonade.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Behaviour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    double cartAbandonmentRate;

    int LoginFrequency;

    int purchaseFrequency;

    Date lastLoginTime;

    double expenditure;

    @CreationTimestamp
    Date dateOfEvaluation;

}
