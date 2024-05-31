package com.example.promonade.repositories;

import com.example.promonade.enums.customerEnums.AgeCategory;
import com.example.promonade.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    long countPurchasesInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    double sumRevenueInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed.id = :promotionId")
    Object sumRevenueInPeriodWithPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("promotionId") Long promotionId);

    @Query("SELECT p.promotionUsed.id FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY p.promotionUsed.id ORDER BY COUNT(p.promotionUsed.id) DESC")
    List<Long> findTop5PromotionsInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT COUNT(p) AS count, DATE(p.timeOfPurchase) AS date FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY DATE(p.timeOfPurchase) ORDER BY DATE(p.timeOfPurchase)")
    List<Object[]> countPurchasesByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT  SUM(p.amountSpent), DATE(p.timeOfPurchase) AS date FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY DATE(p.timeOfPurchase) ORDER BY DATE(p.timeOfPurchase)")
    List<Object[]> sumRevenueByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT p.promotionUsed.name, COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate GROUP BY p.promotionUsed.id, p.promotionUsed.name")
    List<Object[]> countPromotionsUsedInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed.id = :promotionId")
    long countPurchasesInPeriodWithPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("promotionId") Long promotionId);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NOT NULL")
    long countTotalPurchasesInPeriodWithPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate")
    long countTotalPurchasesInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT SUM(p.amountSpent) FROM Purchase p WHERE p.timeOfPurchase BETWEEN :startDate AND :endDate AND p.promotionUsed IS NULL")
    double sumRevenueInPeriodWithoutPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("select sum(p.amountSpent), Date(p.timeOfPurchase) from Purchase p where p.promotionUsed.id=:promotionId and p.timeOfPurchase BETWEEN :startDate AND :endDate group by Date(p.timeOfPurchase)")
    List<Object[]> sumRevenueInPeriodWithPromotionWithDates(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("promotionId") Long promotionId);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.ageCat=:ageCat and p.promotion_used_id=:promotionId and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingAgeForPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("ageCat")String ageCat, @Param("promotionId") Integer promotionId);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.ageCat=:ageCat and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingAge(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("ageCat")String ageCat);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.marital_status=:maritalStatus and p.promotion_used_id=:promotionId and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingMaritalStatusForPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("maritalStatus")String maritalStatus, @Param("promotionId") Integer promotionId);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.marital_status=:maritalStatus and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingMaritalStatus(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("maritalStatus")String maritalStatus);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.gender=:gender and p.promotion_used_id=:promotionId and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingGenderForPromotion(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("gender")String gender, @Param("promotionId") Integer promotionId);

    @Query(value = "select count(p.id) from purchase p join customer c on p.customer_id=c.id join demographics d on c.demographics=d.id where d.gender=:gender and p.time_of_purchase between :startDate AND :endDate", nativeQuery = true)
    double countPurchasesMatchingGender(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("gender")String gender);

}
