package com.example.promonade.controllers;

import com.example.promonade.dto.response.analysticsdtos.*;
import com.example.promonade.models.Promotion;
import com.example.promonade.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;


    @GetMapping("/trending-promotions")
    public ResponseEntity<TrendingCouponsResponse> getTrendingPromotions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Long> promotionIds = analyticsService.getTrendingPromotions(startDate, endDate);
        return ResponseEntity.ok(new TrendingCouponsResponse(promotionIds, true));
    }

    @GetMapping("/active-promotions")
    public ResponseEntity<List<Promotion>> getActivePromotions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(analyticsService.getActivePromotions(startDate, endDate));
    }

    //Promotion
    @GetMapping("/revenue-conversion-rate-with-promotion") //checked
    public ResponseEntity<ConversionRateResponse> getRevenueConversionRateWithPromotion(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        ConversionRateResponse revenueConversionRateWithPromotion = analyticsService.getRevenueConversionRateWithPromotion(startDate, endDate, promotionId);
        return ResponseEntity.ok(revenueConversionRateWithPromotion);
    }

    //Business (Logins vs Time)
    @GetMapping("/login-frequency") // checked
    public ResponseEntity<List<LoginGraphDTO>> getLoginFrequencyByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(analyticsService.getLoginFrequencyByDate(startDate, endDate));
    }

    //Business (Revenue vs Time)
    @GetMapping("/revenue-by-date") // checked
    public ResponseEntity<List<RevenueGraphDTO>> getRevenueByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(analyticsService.getRevenueByDate(startDate, endDate));
    }

    //Business (Purchases freq vs Time)
    @GetMapping("/purchases-by-date") // checked
    public ResponseEntity<List<PurchaseGraphDTO>> getPurchasesByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(analyticsService.getPurchasesByDate(startDate, endDate));
    }

    //Promotion (Revenue vs Time)
    @GetMapping("/promotion-revenue-by-date") // checked
    public ResponseEntity<List<RevenueGraphDTO>> getPromotionRevenueByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        return ResponseEntity.ok(analyticsService.getPromotionRevenueByDate(startDate, endDate, promotionId));
    }

    // promotion
    @GetMapping("/promotion-purchase-conversion-rate") // checked
    public ResponseEntity<ConversionRateResponse> getPromotionPurchaseConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        double conversionRate = analyticsService.getPromotionPurchaseConversionRate(startDate, endDate, promotionId);
        return ResponseEntity.ok(new ConversionRateResponse(conversionRate, true));
    }

    // promotion
    @GetMapping("/promotion-conversion-rate") // checked
    public ResponseEntity<ConversionRateResponse> getPromotionConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        double conversionRate = analyticsService.getPromotionConversionRate(startDate, endDate, promotionId);
        return ResponseEntity.ok(new ConversionRateResponse(conversionRate, true));
    }

    //Business
    @GetMapping("/overall-promotion-conversion-rate")
    public ResponseEntity<ConversionRateResponse> getOverallPromotionConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ){
        ConversionRateResponse conversionRateResponse = analyticsService.getOverallPromotionConversionRate(startDate, endDate);
        return ResponseEntity.ok(conversionRateResponse);
    }

    //Business
    @GetMapping("/purchase-conversion-rate") // ---
    public ResponseEntity<ConversionRateResponse> getPurchaseConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double conversionRate = analyticsService.getPurchaseConversionRate(startDate, endDate);
        return ResponseEntity.ok(new ConversionRateResponse(conversionRate, true));
    }

    //Business
    @GetMapping("/promotion-pie-chart") // ---
    public ResponseEntity<List<PromotionPieChartResponse>> getPromotionPieChart(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(analyticsService.getPromotionPieChart(startDate, endDate));
    }

    //promotion
    @GetMapping("/age-criteria-success-rate")
    public ResponseEntity<ConversionRateResponse> getAgeCriteriaSuccessRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Integer promotionId
    ){
        return ResponseEntity.ok(analyticsService.getAgeCriteriaSuccessRateForPromotion(startDate, endDate, promotionId));
    }

    //promotion
    @GetMapping("/maritalstatus-criteria-success-rate")
    public ResponseEntity<ConversionRateResponse> getMaritalStatusCriteriaSuccessRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Integer promotionId
    ){
        return ResponseEntity.ok(analyticsService.getMaritalStatusCriteriaSuccessRateForPromotion(startDate, endDate, promotionId));
    }

    //promotion
    @GetMapping("/gender-criteria-success-rate")
    public ResponseEntity<ConversionRateResponse> getGenderCriteriaSuccessRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Integer promotionId
    ){
        return ResponseEntity.ok(analyticsService.getGenderCriteriaSuccessRateForPromotion(startDate, endDate, promotionId));
    }
}
