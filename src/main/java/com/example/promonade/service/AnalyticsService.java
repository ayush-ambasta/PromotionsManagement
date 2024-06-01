package com.example.promonade.service;

import com.example.promonade.dto.response.analysticsdtos.*;
import com.example.promonade.exceptions.promotionExceptions.PromotionNotFoundException;
import com.example.promonade.models.Promotion;
import com.example.promonade.models.Purchase;
import com.example.promonade.repositories.BehaviourRepository;
import com.example.promonade.repositories.PromotionRepository;
import com.example.promonade.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.promonade.service.utils.GeneralUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@Service
@AllArgsConstructor
public class AnalyticsService {

    private final PurchaseRepository purchaseRepository;
    private final PromotionRepository promotionRepository;
    private final BehaviourRepository behaviourRepository;
    private final GeneralUtils generalUtils;


    public double getRevenueGenerated(Date startDate, Date endDate) {
        return purchaseRepository.sumRevenueInPeriod(startDate, endDate);
    }

    public double getRevenueGeneratedWithPromotion(Date startDate, Date endDate, Long promotionId) {
        Object revenue = purchaseRepository.sumRevenueInPeriodWithPromotion(startDate, endDate, promotionId);
        if(revenue==null) {
            return 0.0;
        }
        return (double) revenue;
    }

    public ConversionRateResponse getRevenueConversionRateWithPromotion(Date startDate, Date endDate, Long promotionId) {
        double totalRevenue = getRevenueGenerated(startDate, endDate);
        double revenueWithPromotion = getRevenueGeneratedWithPromotion(startDate, endDate, promotionId);
        double revenueConversionRate = totalRevenue==0? 0:revenueWithPromotion/totalRevenue*100;
        return new ConversionRateResponse(generalUtils.round(revenueConversionRate, 1), true);
    }

    public List<Long> getTrendingPromotions(Date startDate, Date endDate) {
        return purchaseRepository.findTop5PromotionsInPeriod(startDate, endDate);
    }

    public List<Promotion> getActivePromotions(Date startDate, Date endDate) {
        return promotionRepository.findActivePromotionsInPeriod(startDate, endDate);
    }

    public List<LoginGraphDTO> getLoginFrequencyByDate(Date startDate, Date endDate) {
        List<Object[]> results = behaviourRepository.countLoginsByDate(startDate, endDate);
        // Map projection to DTO
        ArrayList<LoginGraphDTO> loginGraphDTOs = new ArrayList<>();
        for (Object[] result : results) {
            Long sum = (Long)result[0];
            Date date = (Date) result[1];
            loginGraphDTOs.add(new LoginGraphDTO(date, sum));
        }

        return loginGraphDTOs;
    }

    public List<RevenueGraphDTO> getPromotionRevenueByDate(Date startDate, Date endDate, Long promotionId) {
        List<Object[]> resultList = purchaseRepository.sumRevenueInPeriodWithPromotionWithDates(startDate, endDate, promotionId);

        // Map projection to DTO
        ArrayList<RevenueGraphDTO> revenueGraphDTOs = new ArrayList<>();
        for (Object[] result : resultList) {
            Double sum = (Double) result[0];
            Date date = (Date) result[1];
            revenueGraphDTOs.add(new RevenueGraphDTO(date, sum));
        }

        return revenueGraphDTOs;

    }
    public List<RevenueGraphDTO> getRevenueByDate(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.sumRevenueByDate(startDate, endDate);
        // Map projection to DTO
        ArrayList<RevenueGraphDTO> revenueGraphDTOs = new ArrayList<>();
        for (Object[] result : results) {
            Double sum = (Double) result[0];
            Date date = (Date) result[1];
            revenueGraphDTOs.add(new RevenueGraphDTO(date, sum));
        }

        return revenueGraphDTOs;
    }

    public List<PurchaseGraphDTO> getPurchasesByDate(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.countPurchasesByDate(startDate, endDate);
        ArrayList<PurchaseGraphDTO> purchaseGraphDTOs = new ArrayList<>();
        for (Object[] result : results) {
            Long sum = (Long)result[0];
            Date date = (Date) result[1];
            purchaseGraphDTOs.add(new PurchaseGraphDTO(date, sum));
        }

        return purchaseGraphDTOs;
    }

    public double getPromotionPurchaseConversionRate(Date startDate, Date endDate, Long promotionId) {
        long totalLogins = behaviourRepository.countLoginsInPeriod(startDate, endDate);
        long promotionPurchases = purchaseRepository.countPurchasesInPeriodWithPromotion(startDate, endDate, promotionId);
        double conversionRate = totalLogins == 0 ? 0 : (double) promotionPurchases / totalLogins*100;
        return generalUtils.round(conversionRate, 1);
    }

    public double getPromotionConversionRate(Date startDate, Date endDate, Long promotionId) {
        long totalPurchases = purchaseRepository.countPurchasesInPeriod(startDate, endDate);
        long promotionPurchases = purchaseRepository.countPurchasesInPeriodWithPromotion(startDate, endDate, promotionId);
        double conversionRate = totalPurchases == 0 ? 0 : (double) promotionPurchases / totalPurchases *100;
        return generalUtils.round(conversionRate, 1);
    }

    public double getPurchaseConversionRate(Date startDate, Date endDate) {
        long totalLogins = behaviourRepository.countLoginsInPeriod(startDate, endDate);
        long totalPurchases = purchaseRepository.countPurchasesInPeriod(startDate, endDate);
        double conversionRate = totalLogins == 0 ? 0 : (double) totalPurchases / totalLogins * 100; // Conversion rate in percentage
        return generalUtils.round(conversionRate, 1);
    }

    public List<PromotionPieChartResponse> getPromotionPieChart(Date startDate, Date endDate) {
        List<Object[]> results = purchaseRepository.countPromotionsUsedInPeriod(startDate, endDate);
        ArrayList<PromotionPieChartResponse> promoChartDTOs = new ArrayList<>();
        for (Object[] result : results) {
            String name = (String)result[0];
            Long count = (Long) result[1];
            promoChartDTOs.add(new PromotionPieChartResponse(name, count));
        }

        return promoChartDTOs;
    }

    public ConversionRateResponse getOverallPromotionConversionRate(Date startDate, Date endDate) {
        long totalPurchases = purchaseRepository.countTotalPurchasesInPeriod(startDate, endDate);
        long totalPurchasesWithPromotions = purchaseRepository.countTotalPurchasesInPeriodWithPromotion(startDate, endDate);
        double overallPromotionConversionRate = (totalPurchases==0)? 0: (double)totalPurchasesWithPromotions/totalPurchases*100;
        return new ConversionRateResponse(generalUtils.round(overallPromotionConversionRate,1), true);
    }

    public ConversionRateResponse getAgeCriteriaSuccessRateForPromotion(Date startDate, Date endDate, Integer promotionId) {
        Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);
        if(optionalPromotion.isEmpty()) {
            throw new PromotionNotFoundException("Promotion not found for Id: " + promotionId);
        }
        Promotion promotion = optionalPromotion.get();
        double countPurchasesMatchingCriteriaForPromotion = purchaseRepository.countPurchasesMatchingAgeForPromotion(startDate, endDate, promotion.getCriteria().getAgeCategory().toString(), promotionId);
        double countPurchasesMatchingCriteria = purchaseRepository.countPurchasesMatchingAge(startDate, endDate, promotion.getCriteria().getAgeCategory().toString());
        double successRate = countPurchasesMatchingCriteria == 0? 0: countPurchasesMatchingCriteriaForPromotion/countPurchasesMatchingCriteria * 100;
        return new ConversionRateResponse(generalUtils.round(successRate, 1), true);
    }

    public ConversionRateResponse getMaritalStatusCriteriaSuccessRateForPromotion(Date startDate, Date endDate, Integer promotionId) {
        Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);
        if(optionalPromotion.isEmpty()) {
            throw new PromotionNotFoundException("Promotion not found for Id: " + promotionId);
        }
        Promotion promotion = optionalPromotion.get();
        double countPurchasesMatchingCriteriaForPromotion = purchaseRepository.countPurchasesMatchingMaritalStatusForPromotion(startDate, endDate, promotion.getCriteria().getMaritalStatus().toString(), promotionId);
        double countPurchasesMatchingCriteria = purchaseRepository.countPurchasesMatchingMaritalStatus(startDate, endDate, promotion.getCriteria().getMaritalStatus().toString());
        double successRate = countPurchasesMatchingCriteria == 0? 0: countPurchasesMatchingCriteriaForPromotion/countPurchasesMatchingCriteria * 100;
        return new ConversionRateResponse(generalUtils.round(successRate, 1), true);
    }

    public ConversionRateResponse getGenderCriteriaSuccessRateForPromotion(Date startDate, Date endDate, Integer promotionId) {
        Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);
        if(optionalPromotion.isEmpty()) {
            throw new PromotionNotFoundException("Promotion not found for Id: " + promotionId);
        }
        Promotion promotion = optionalPromotion.get();
        double countPurchasesMatchingCriteriaForPromotion = purchaseRepository.countPurchasesMatchingGenderForPromotion(startDate, endDate, promotion.getCriteria().getGender().toString(), promotionId);
        double countPurchasesMatchingCriteria = purchaseRepository.countPurchasesMatchingGender(startDate, endDate, promotion.getCriteria().getGender().toString());
        double successRate = countPurchasesMatchingCriteria == 0? 0: countPurchasesMatchingCriteriaForPromotion/countPurchasesMatchingCriteria * 100;
        return new ConversionRateResponse(generalUtils.round(successRate, 1), true);
    }
}
