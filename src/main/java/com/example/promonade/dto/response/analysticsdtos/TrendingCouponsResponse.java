package com.example.promonade.dto.response.analysticsdtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrendingCouponsResponse {
    private List<Long> trendingCoupons;
    private boolean success;
}
