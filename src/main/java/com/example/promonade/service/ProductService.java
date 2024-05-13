package com.example.promonade.service;
import com.example.promonade.dto.request.productservicedtos.ProductRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.exceptions.productServiceExceptions.ProductIncompleteException;
import com.example.promonade.exceptions.productServiceExceptions.ProductNotFoundException;

import com.example.promonade.repositories.ProductRepository;
import com.example.promonade.service.transformers.Product_ServiceTransformer;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.example.promonade.models.Product;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Product addProduct(ProductRequest productRequest) {
        if(productRequest.getName()==null){
            throw new ProductIncompleteException("Product name cannot be empty");
        }
        if(productRequest.getDescription()==null){
            throw new ProductIncompleteException("Product description cannot be empty");
        }
        if(productRequest.getPrice()==null){
            throw new ProductIncompleteException("Product price cannot be empty");
        }
        Product product = Product_ServiceTransformer.productRequestToProduct(productRequest);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found for id " +productId);
        }
        return productOptional.get();
    }

    public Product updateProduct(int productId, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found for id " +productId);
        }
        Product existingProduct = productOptional.get();

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());

        return productRepository.save(existingProduct);
    }

    public UpdationResponse deleteProduct(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found for id " +productId);
        }
        Product product = productOptional.get();
        productRepository.delete(product);
        return new UpdationResponse(String.format("Product %s with id %d is successfully deleted", product.getName(), productId), true);
    }


}
