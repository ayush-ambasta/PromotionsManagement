package com.example.promonade.dto.response;

import com.example.promonade.models.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDTO {
    // Getters and setters
    private Long id;
    private String name;
    private String description;
    private double price;

    // Constructors
    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductResponseDTO(Product product) {
    }
}
