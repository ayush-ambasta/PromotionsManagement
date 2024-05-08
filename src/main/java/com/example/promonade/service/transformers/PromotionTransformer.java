package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.exceptions.userExceptions.UserNotFoundException;
import com.example.promonade.models.Criteria;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;


public class PromotionTransformer {

    public static Promotion promotionRequestToPromotion(PromotionRequest promotionRequest, User createdBy){

        return Promotion.builder()
                .promotionType(promotionRequest.getPromotionType())
                .name(promotionRequest.getName())
                .approved(false)
                .validTill(promotionRequest.getValidTill())
                .validFrom(promotionRequest.getValidFrom())
                .category(promotionRequest.getCategory())
                .createdBy(createdBy)
                .criteria(Criteria.builder()
                        .ageCategory(promotionRequest.getCriteria().getAgeCategory())
                        .gender(promotionRequest.getCriteria().getGender())
                        .maritalStatus(promotionRequest.getCriteria().getMaritalStatus())
                        .productType(promotionRequest.getCriteria().getProductType())
                        .build())
                .build();
    }
}
