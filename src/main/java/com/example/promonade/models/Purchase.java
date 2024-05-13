package com.example.promonade.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name="purchased_products")
//    List<Product> productList = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name="purchased_services")
//    List<ServiceProvided> serviceList = new ArrayList<>();

    @CreationTimestamp
    Date timeOfPurchase;

    double amountSpent;

    @OneToOne(cascade = CascadeType.ALL)
    Promotion promotionUsed;

}
