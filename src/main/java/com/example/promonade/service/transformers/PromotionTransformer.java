package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.models.Criteria;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class PromotionTransformer {

    private final UserRepository userRepository;

    public Promotion promotionRequestToPromotion(PromotionRequest promotionRequest){
        Optional<User> optionalCreatedBy = userRepository.findByEmail(promotionRequest.getUserEmail());
        if(optionalCreatedBy.isEmpty()){
            throw new RuntimeException("User with the specified Mail is not found!");
        }
        User createdBy = optionalCreatedBy.get();

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
