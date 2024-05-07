package com.example.promonade.controllers;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.service.PromotionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/promotions")
public class PromotionsController {

    private final PromotionsService promotionsService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest){
        return ResponseEntity.ok(promotionsService.createPromotion(promotionRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> deletePromotion(@PathVariable("id") int id){
        return ResponseEntity.ok(promotionsService.deletePromotion(id));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getAllPromotions(){
        return ResponseEntity.ok(promotionsService.getAllPromotions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getPromotion(@PathVariable int id){
        return ResponseEntity.ok(promotionsService.getPromotion(id));
    }

    //Have to implement

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> editPromotion(@PathVariable int id){
        return ResponseEntity.ok("Not implemented!");
    }

    @GetMapping("/get-non-approved-per-team")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getNonApprovedPromotions(@RequestParam("team") Team team){
        return ResponseEntity.ok("Not implemented!");
    }

    @PostMapping("/approve-promotion")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> approvePromotion(@RequestParam("team") Team team){
        return ResponseEntity.ok("Not implemented!");
    }
}
