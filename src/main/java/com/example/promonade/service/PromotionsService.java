package com.example.promonade.service;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.User;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.service.transformers.PromotionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PromotionsService {
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;

    public Promotion createPromotion(PromotionRequest promotionRequest){
        if(promotionRequest.getCategory()==null){
            throw new RuntimeException("Promotion category cannot be empty!");
        }
        if(promotionRequest.getName()==null){
            throw new RuntimeException("Promotion name cannot be empty!");
        }
        if(promotionRequest.getCriteria()==null){
            throw new RuntimeException("Promotion criteria cannot be empty!");
        }
        if(promotionRequest.getValidFrom()==null || promotionRequest.getValidTill()==null){
            throw new RuntimeException("Promotion validity cannot be empty!");
        }
        if(promotionRequest.getPromotionType()==null){
            throw new RuntimeException("Promotion type cannot be empty!");
        }
        Optional<User> optionalCreatedBy = userRepository.findByEmail(promotionRequest.getUserEmail());
        if(optionalCreatedBy.isEmpty()){
            throw new RuntimeException("User with the specified Mail is not found!");
        }
        User createdBy = optionalCreatedBy.get();
        Promotion promotion = PromotionTransformer.promotionRequestToPromotion(promotionRequest, createdBy);

        promotionRepository.save(promotion);

        return promotion;
    }
}
