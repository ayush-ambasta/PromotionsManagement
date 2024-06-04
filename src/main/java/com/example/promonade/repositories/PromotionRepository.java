package com.example.promonade.repositories;

import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("select p from Promotion p where p.approved = false and p.category = :category and p.decline = false")
    List<Promotion> findNonApprovedPromotionsByCategory(@Param("category")PromotionCategory category);

    @Query("select p from Promotion p where p.approved = true and p.category = :category")
    List<Promotion> findApprovedPromotionsByCategory(@Param("category")PromotionCategory category);

    @Query("select p from Promotion p where p.approved = true")
    List<Promotion> findApprovedPromotions();

    @Query("SELECT p FROM Promotion p WHERE p.validFrom <= :endDate AND p.validTill >= :startDate AND p.approved = true")
    List<Promotion> findActivePromotionsInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

}
