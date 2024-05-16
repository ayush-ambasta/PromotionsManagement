package com.example.promonade;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

@OpenAPIDefinition(info = @Info(title = "PromonadeApplication api", description = "Documentation PromonadeApplication API v1.0"))
public class PromonadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromonadeApplication.class, args);
	}

}
