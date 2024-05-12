package com.example.promonade.controllers;

import com.example.promonade.dto.response.ProductResponseDTO;
import com.example.promonade.models.Product;
import com.example.promonade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody com.example.promonade.dto.request.ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO.getName(), productRequestDTO.getDescription(), productRequestDTO.getPrice());
        Product addedProduct = productService.addProduct(product);
        ProductResponseDTO responseDTO = new ProductResponseDTO(addedProduct.getId(), addedProduct.getName(), addedProduct.getDescription(), addedProduct.getPrice());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId, @RequestBody com.example.promonade.dto.request.ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO.getName(), productRequestDTO.getDescription(), productRequestDTO.getPrice());
        Product updatedProduct = productService.updateProduct(productId, product);
        ProductResponseDTO responseDTO = new ProductResponseDTO(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
