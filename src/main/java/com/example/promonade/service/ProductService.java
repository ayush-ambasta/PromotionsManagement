package com.example.promonade.service;
import com.example.promonade.dto.response.ProductResponseDTO;
import com.example.promonade.exceptions.promotionExceptions.ResourceNotFoundException;
import com.example.promonade.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import com.example.promonade.models.Product;
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long productId) {
        Product product = getProductFromRepository(productId);
        return new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = getProductFromRepository(productId);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        // Set other attributes as needed
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product getProductFromRepository(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

}
