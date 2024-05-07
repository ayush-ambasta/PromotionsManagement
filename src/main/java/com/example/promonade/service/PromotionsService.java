package com.example.promonade.service;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.exceptions.promotionExceptions.PromotionIncompleteException;
import com.example.promonade.exceptions.promotionExceptions.PromotionNotFoundException;
import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.service.transformers.PromotionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PromotionsService {
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final PromotionTransformer promotionTransformer;

    public Promotion createPromotion(PromotionRequest promotionRequest){
        if(promotionRequest.getCategory()==null){
            throw new PromotionIncompleteException("Promotion category cannot be empty!");
        }
        if(promotionRequest.getName()==null){
            throw new PromotionIncompleteException("Promotion name cannot be empty!");
        }
        if(promotionRequest.getCriteria()==null){
            throw new PromotionIncompleteException("Promotion criteria cannot be empty!");
        }
        if(promotionRequest.getValidFrom()==null || promotionRequest.getValidTill()==null){
            throw new PromotionIncompleteException("Promotion validity cannot be empty!");
        }
        if(promotionRequest.getPromotionType()==null){
            throw new PromotionIncompleteException("Promotion type cannot be empty!");
        }

        Promotion promotion = promotionTransformer.promotionRequestToPromotion(promotionRequest);

        promotionRepository.save(promotion);

        return promotion;
    }

    public String deletePromotion(int id) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        Promotion promotion = promotionOptional.get();
        promotionRepository.delete(promotion);
        return String.format("Promotion %d with the name %s successfully deleted!", promotion.getId(), promotion.getName());
    }

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion getPromotion(int id){
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if(promotionOptional.isEmpty()){
            throw new PromotionNotFoundException("Promotion with id " + id + " does not exist");
        }
        return promotionOptional.get();
    }
}
