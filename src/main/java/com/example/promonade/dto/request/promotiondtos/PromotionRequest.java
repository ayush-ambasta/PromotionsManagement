package com.example.promonade.dto.request.promotiondtos;

import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.enums.promotionEnums.PromotionType;
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
public class PromotionRequest {
    PromotionCategory category;

    String name;

    PromotionType promotionType;

    Date validFrom;

    Date validTill;

    CriteriaRequest criteria;

    String userEmail;
}
