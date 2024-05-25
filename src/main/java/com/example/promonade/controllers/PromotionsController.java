package com.example.promonade.controllers;

import com.example.promonade.dto.request.promotiondtos.PromotionRequest;
import com.example.promonade.service.PromotionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/promotions")
public class PromotionsController {

    private final PromotionsService promotionsService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> createPromotion(@RequestHeader("Authorization") String headerAuth,
                                             @RequestBody PromotionRequest promotionRequest) {
        return ResponseEntity.ok(promotionsService.createPromotion(promotionRequest, headerAuth));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> deletePromotion(@PathVariable("id") int id, @RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.deletePromotion(id, headerAuth));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getAllPromotions() {
        return ResponseEntity.ok(promotionsService.getAllPromotions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getPromotion(@PathVariable int id) {
        return ResponseEntity.ok(promotionsService.getPromotion(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> editPromotion(@PathVariable int id, @RequestBody PromotionRequest promotionRequest) {
        return ResponseEntity.ok(promotionsService.editPromotion(id, promotionRequest));
    }

    @GetMapping("/get-non-approved-user-team")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getNonApprovedPromotionsForUserTeam(@RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.getNonApprovedPromotionsForUserTeam(headerAuth));
    }

    @GetMapping("/get-approved-user-team")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getApprovedPromotionsForUserTeam(@RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.getApprovedPromotionsForUserTeam(headerAuth));
    }

    @GetMapping("/get-approved")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> getApprovedPromotions() {
        return ResponseEntity.ok(promotionsService.getApprovedPromotions());
    }

    @PostMapping("/approve-promotion")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> approvePromotion(@RequestParam("id") int id, @RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.approvePromotion(id, headerAuth));
    }

    @PostMapping("/disapprove-promotion")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> disapprovePromotion(@RequestParam("id") int id, @RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.disapprovePromotion(id, headerAuth));
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('OWNER')")
    public ResponseEntity<?> deactivatePromotion(@RequestParam("id") int id, @RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(promotionsService.deactivatePromotion(id, headerAuth));
    }

    //Just for quick dev purpose
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
    public ResponseEntity<?> deleteAllPromotions() {
        return ResponseEntity.ok(promotionsService.deleteAllPromotions());
    }

}