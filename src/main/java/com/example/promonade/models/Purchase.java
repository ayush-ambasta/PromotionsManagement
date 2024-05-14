package com.example.promonade.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    //product and services will have many to many relation because multiple customer's purchases can have same products or services
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="purchase_id")
    List<Product> productList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="purchase_id")
    List<Service> serviceList = new ArrayList<>();

    @CreationTimestamp
    Date timeOfPurchase;

    double amountSpent;

    @OneToOne(cascade = CascadeType.ALL)
    Promotion promotionUsed;

}
