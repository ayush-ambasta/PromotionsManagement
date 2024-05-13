package com.example.promonade.repositories;

import java.util.List;
import java.util.Optional;

import com.example.promonade.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}


