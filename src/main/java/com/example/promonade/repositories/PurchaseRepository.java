package com.example.promonade.repositories;

import com.example.promonade.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    long countPurchasesInPeriod(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    double sumRevenueInPeriod(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NOT NULL")
    double sumRevenueWithPromotion(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NULL")
    double sumRevenueWithoutPromotion(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed.name = :couponCode")
    double sumRevenueInPeriodWithCoupon(Date startDate, Date endDate, String couponCode);

    @Query("SELECT p.promotionUsed.name FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY p.promotionUsed.name ORDER BY COUNT(p.promotionUsed.name) DESC")
    List<String> findTop5CouponsInPeriod(Date startDate, Date endDate);
}
