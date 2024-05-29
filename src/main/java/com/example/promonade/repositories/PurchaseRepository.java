package com.example.promonade.repositories;

import com.example.promonade.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    long countPurchasesInPeriod(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    double sumRevenueInPeriod(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed.id = :promotionId")
    double sumRevenueInPeriodWithPromotion(Date startDate, Date endDate, Long promotionId);

    @Query("SELECT p.promotionUsed.id FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY p.promotionUsed.id ORDER BY COUNT(p.promotionUsed.id) DESC")
    List<Long> findTop5PromotionsInPeriod(Date startDate, Date endDate);

    @Query("SELECT DATE(p.timeOfPurchase) AS date, COUNT(p) AS count FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY DATE(p.timeOfPurchase) ORDER BY DATE(p.timeOfPurchase)")
    List<Object[]> countPurchasesByDate(Date startDate, Date endDate);

    @Query("SELECT DATE(p.timeOfPurchase) AS date, SUM(p.amountSpent) AS total FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY DATE(p.timeOfPurchase) ORDER BY DATE(p.timeOfPurchase)")
    List<Object[]> sumRevenueByDate(Date startDate, Date endDate);

    @Query("SELECT p.promotionUsed.id, COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY p.promotionUsed.id")
    List<Object[]> countPromotionsUsedInPeriod(Date startDate, Date endDate);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed.id = :promotionId")
    long countPurchasesInPeriodWithPromotion(Date startDate, Date endDate, Long promotionId);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NULL")
    long countPurchasesInPeriodWithoutPromotion(Date startDate, Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NULL")
    double sumRevenueInPeriodWithoutPromotion(Date startDate, Date endDate);


}
