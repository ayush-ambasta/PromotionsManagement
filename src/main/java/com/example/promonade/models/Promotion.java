package com.example.promonade.models;

import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.enums.promotionEnums.PromotionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    PromotionCategory category;

    String name;

    @Enumerated(EnumType.STRING)
    PromotionType promotionType;

    Date validFrom;

    Date validTill;

    Boolean approved;

    @ManyToOne
    @JoinColumn
    User createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Criteria criteria;

    @CreationTimestamp
    Date createdAt;

    boolean decline = false;

    boolean active = false;
}
