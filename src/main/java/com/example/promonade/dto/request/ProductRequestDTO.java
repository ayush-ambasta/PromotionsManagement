package com.example.promonade.dto.request;

import lombok.Getter;
import lombok.Setter;

// ProductRequestDTO.java
@Setter
@Getter

public class ProductRequestDTO {
    // Getters and setters
    private String name;
    private String description;
    private double price;

    // Constructors
    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
