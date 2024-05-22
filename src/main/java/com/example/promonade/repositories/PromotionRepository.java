package com.example.promonade.repositories;

import com.example.promonade.enums.promotionEnums.PromotionCategory;
import com.example.promonade.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("select p from Promotion p where p.approved = false and category = :category and decline = false")
    List<Promotion> findNonApprovedPromotionsByCategory(@Param("category")PromotionCategory category);

    @Query("select p from Promotion p where p.approved = true and category = :category")
    List<Promotion> findApprovedPromotionsByCategory(@Param("category")PromotionCategory category);
}
