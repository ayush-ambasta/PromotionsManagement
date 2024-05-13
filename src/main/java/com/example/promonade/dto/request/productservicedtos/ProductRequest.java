package com.example.promonade.dto.request.productservicedtos;

import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    // Getters and setters
    private String name;
    private String description;
    private Double price;
}
