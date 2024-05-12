package com.example.promonade.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceRequestDTO {
    // Getters and setters
    private String name;
    private String description;
    private double price;

    // Constructors
    public ServiceRequestDTO() {
    }

    public ServiceRequestDTO(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
