package com.example.promonade.service;

import com.example.promonade.dto.response.analysticsdtos.ConversionRateResponse;
import com.example.promonade.dto.response.analysticsdtos.RevenueResponse;
import com.example.promonade.dto.response.analysticsdtos.TrendingCouponsResponse;
import com.example.promonade.dto.response.analysticsdtos.PromotionPieChartResponse;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.Purchase;
import com.example.promonade.repositories.CustomerRepository;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnalyticsService {

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    private final PromotionRepository promotionRepository;

    public double getConversionRate(Date startDate, Date endDate) {
        long totalLogins = customerRepository.countLoginsInPeriod(startDate, endDate);
        long totalPurchases = purchaseRepository.countPurchasesInPeriod(startDate, endDate);
        return totalLogins == 0 ? 0 : (double) totalPurchases / totalLogins;
    }

    public double getRevenueGenerated(Date startDate, Date endDate) {
        return purchaseRepository.sumRevenueInPeriod(startDate, endDate);
    }

    public double getRevenueGeneratedWithPromotion(Date startDate, Date endDate, Long promotionId) {
        return purchaseRepository.sumRevenueInPeriodWithPromotion(startDate, endDate, promotionId);
    }

    public double getRevenueGeneratedWithoutPromotion(Date startDate, Date endDate) {
        return purchaseRepository.sumRevenueInPeriodWithoutPromotion(startDate, endDate);
    }

    public List<Long> getTrendingPromotions(Date startDate, Date endDate) {
        return purchaseRepository.findTop5PromotionsInPeriod(startDate, endDate);
    }

    public List<Promotion> getActivePromotions(Date startDate, Date endDate) {
        return promotionRepository.findActivePromotionsInPeriod(startDate, endDate);
    }

    public List<Map<String, Object>> getLoginFrequencyByDate(Date startDate, Date endDate) {
        List<Object[]> results = customerRepository.countLoginsByDate(startDate, endDate);
        return results.stream()
                .map(result -> Map.of("date", result[0], "count", result[1]))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRevenueByDate(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.sumRevenueByDate(startDate, endDate);
        return results.stream()
                .map(result -> Map.of("date", result[0], "total", result[1]))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getPurchasesByDate(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.countPurchasesByDate(startDate, endDate);
        return results.stream()
                .map(result -> Map.of("date", result[0], "count", result[1]))
                .collect(Collectors.toList());
    }

    public double getPromotionPurchaseConversionRate(Date startDate, Date endDate, Long promotionId) {
        long totalLogins = customerRepository.countLoginsInPeriod(startDate, endDate);
        long promotionPurchases = purchaseRepository.countPurchasesInPeriodWithPromotion(startDate, endDate, promotionId);
        return totalLogins == 0 ? 0 : (double) promotionPurchases / totalLogins;
    }

    public double getPromotionConversionRate(Date startDate, Date endDate, Long promotionId) {
        long totalPurchases = purchaseRepository.countPurchasesInPeriod(startDate, endDate);
        long promotionPurchases = purchaseRepository.countPurchasesInPeriodWithPromotion(startDate, endDate, promotionId);
        return totalPurchases == 0 ? 0 : (double) promotionPurchases / totalPurchases;
    }

    public double getPurchaseConversionRate(Date startDate, Date endDate) {
        long totalLogins = customerRepository.countLoginsInPeriod(startDate, endDate);
        long totalPurchases = purchaseRepository.countPurchasesInPeriod(startDate, endDate);
        return totalLogins == 0 ? 0 : (double) totalPurchases / totalLogins * 100; // Conversion rate in percentage
    }

    public PromotionPieChartResponse getPromotionPieChart(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.countPromotionsUsedInPeriod(startDate, endDate);
        List<Long> promotionIds = results.stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());
        List<Long> frequencies = results.stream()
                .map(result -> (Long) result[1])
                .collect(Collectors.toList());
        return new PromotionPieChartResponse(promotionIds, frequencies);
    }
}
