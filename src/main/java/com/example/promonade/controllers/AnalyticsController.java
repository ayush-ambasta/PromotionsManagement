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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/conversion-rate")
    public ConversionRateResponse getConversionRate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double conversionRate = analyticsService.getConversionRate(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
        return new ConversionRateResponse(conversionRate);
    }

    @GetMapping("/revenue-generated")
    public RevenueResponse getRevenueGenerated(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double totalRevenue = analyticsService.getRevenueGenerated(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
        return new RevenueResponse(totalRevenue);
    }

    @GetMapping("/revenue-with-coupon")
    public RevenueResponse getRevenueGeneratedWithCoupon(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam("couponCode") String couponCode) {
        double totalRevenue = analyticsService.getRevenueGeneratedWithCoupon(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()), couponCode);
        return new RevenueResponse(totalRevenue);
    }

    @GetMapping("/revenue-with-promotion")
    public RevenueResponse getRevenueWithPromotion(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double totalRevenue = analyticsService.getRevenueWithPromotion(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
        return new RevenueResponse(totalRevenue);
    }

    @GetMapping("/revenue-without-promotion")
    public RevenueResponse getRevenueWithoutPromotion(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        double totalRevenue = analyticsService.getRevenueWithoutPromotion(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
        return new RevenueResponse(totalRevenue);
    }

    @GetMapping("/trending-coupons")
    public TrendingCouponsResponse getTrendingCoupons(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<String> trendingCoupons = analyticsService.getTrendingCoupons(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
        return new TrendingCouponsResponse(trendingCoupons);
    }

    @GetMapping("/active-promotions")
    public List<Promotion> getActivePromotions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return analyticsService.getActivePromotions(java.sql.Date.valueOf(startDate.toLocalDate()), java.sql.Date.valueOf(endDate.toLocalDate()));
    }
}
