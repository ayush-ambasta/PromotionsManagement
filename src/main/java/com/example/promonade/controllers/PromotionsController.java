package com.example.promonade.controllers;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.service.PromotionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/promotions")
public class PromotionsController {

    private final PromotionsService promotionsService;

    @PostMapping("/create")
    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest){
        return ResponseEntity.ok(promotionsService.createPromotion(promotionRequest));
    }
}
