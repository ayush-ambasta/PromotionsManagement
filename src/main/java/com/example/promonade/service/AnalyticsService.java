package com.example.promonade.service;

import com.example.promonade.models.Promotion;
import com.example.promonade.repositories.CustomerRepository;
import com.example.promonade.repositories.PurchaseRepository;
import com.example.promonade.repositories.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public double getRevenueGeneratedWithCoupon(Date startDate, Date endDate, String couponCode) {
        return purchaseRepository.sumRevenueInPeriodWithCoupon(startDate, endDate, couponCode);
    }

    public double getRevenueWithPromotion(Date startDate, Date endDate) {
        return purchaseRepository.sumRevenueWithPromotion(startDate, endDate);
    }

    public double getRevenueWithoutPromotion(Date startDate, Date endDate) {
        return purchaseRepository.sumRevenueWithoutPromotion(startDate, endDate);
    }

    public List<String> getTrendingCoupons(Date startDate, Date endDate) {
        return purchaseRepository.findTop5CouponsInPeriod(startDate, endDate);
    }

    public List<Promotion> getActivePromotions(Date startDate, Date endDate) {
        return promotionRepository.findActivePromotionsInPeriod(startDate, endDate);
    }
}
