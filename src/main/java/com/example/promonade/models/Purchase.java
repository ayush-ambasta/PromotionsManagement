package com.example.promonade.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "purchased_products",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> productList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "purchased_services",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    List<Service> serviceList = new ArrayList<>();

    Date timeOfPurchase;

    double amountSpent;

    @ManyToOne(cascade = CascadeType.ALL)
    Promotion promotionUsed;

}
