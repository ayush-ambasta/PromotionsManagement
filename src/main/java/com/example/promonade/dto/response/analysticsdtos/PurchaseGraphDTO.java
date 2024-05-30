package com.example.promonade.dto.response.analysticsdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseGraphDTO {
    private Date date;
    private double purchases;
}
