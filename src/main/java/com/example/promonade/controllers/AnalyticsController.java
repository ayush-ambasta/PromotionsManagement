package com.example.promonade.controllers;

import com.example.promonade.dto.response.analysticsdtos.ConversionRateResponse;
import com.example.promonade.dto.response.analysticsdtos.RevenueResponse;
import com.example.promonade.dto.response.analysticsdtos.TrendingCouponsResponse;
import com.example.promonade.models.Promotion;
import com.example.promonade.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/conversion-rate")
    public ConversionRateResponse getConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double conversionRate = analyticsService.getConversionRate(startDate, endDate);
        return new ConversionRateResponse(conversionRate);
    }

    @GetMapping("/revenue-generated")
    public RevenueResponse getRevenueGenerated(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double revenue = analyticsService.getRevenueGenerated(startDate, endDate);
        return new RevenueResponse(revenue);
    }

    @GetMapping("/revenue-generated-with-promotion")
    public RevenueResponse getRevenueGeneratedWithPromotion(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        double revenue = analyticsService.getRevenueGeneratedWithPromotion(startDate, endDate, promotionId);
        return new RevenueResponse(revenue);
    }

    @GetMapping("/revenue-generated-without-promotion")
    public RevenueResponse getRevenueGeneratedWithoutPromotion(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double revenue = analyticsService.getRevenueGeneratedWithoutPromotion(startDate, endDate);
        return new RevenueResponse(revenue);
    }

    @GetMapping("/trending-promotions")
    public TrendingCouponsResponse getTrendingPromotions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Long> promotionIds = analyticsService.getTrendingPromotions(startDate, endDate);
        return new TrendingCouponsResponse(promotionIds);
    }

    @GetMapping("/active-promotions")
    public List<Promotion> getActivePromotions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return analyticsService.getActivePromotions(startDate, endDate);
    }

    @GetMapping("/login-frequency")
    public List<Map<String, Object>> getLoginFrequencyByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return analyticsService.getLoginFrequencyByDate(startDate, endDate);
    }

    @GetMapping("/revenue-by-date")
    public List<Map<String, Object>> getRevenueByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return analyticsService.getRevenueByDate(startDate, endDate);
    }

    @GetMapping("/purchases-by-date")
    public List<Map<String, Object>> getPurchasesByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return analyticsService.getPurchasesByDate(startDate, endDate);
    }

    @GetMapping("/promotion-purchase-conversion-rate")
    public ConversionRateResponse getPromotionPurchaseConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        double conversionRate = analyticsService.getPromotionPurchaseConversionRate(startDate, endDate, promotionId);
        return new ConversionRateResponse(conversionRate);
    }

    @GetMapping("/promotion-conversion-rate")
    public ConversionRateResponse getPromotionConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("promotionId") Long promotionId) {
        double conversionRate = analyticsService.getPromotionConversionRate(startDate, endDate, promotionId);
        return new ConversionRateResponse(conversionRate);
    }

    @GetMapping("/purchase-conversion-rate")
    public ConversionRateResponse getPurchaseConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double conversionRate = analyticsService.getPurchaseConversionRate(startDate, endDate);
        return new ConversionRateResponse(conversionRate);
    }

    @GetMapping("/promotion-pie-chart")
    public com.example.promonade.dto.response.analysticsdtos.PromotionPieChartResponse getPromotionPieChart(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return analyticsService.getPromotionPieChart(startDate, endDate);
    }
}
