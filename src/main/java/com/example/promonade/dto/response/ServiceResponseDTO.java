package com.example.promonade.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceResponseDTO {
    // Getters and setters
    private Long id;
    private String name;
    private String description;
    private double price;

    // Constructors
    public ServiceResponseDTO() {
    }

    public ServiceResponseDTO(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
