package com.example.promonade.repositories;

import java.util.List;
import java.util.Optional;

import com.example.promonade.models.Product;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product save(Product existingProduct);

    void deleteById(Long productId);
}


