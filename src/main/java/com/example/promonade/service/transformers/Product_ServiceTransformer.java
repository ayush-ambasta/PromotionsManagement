package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.productservicedtos.ProductRequest;
import com.example.promonade.dto.request.productservicedtos.ServiceRequest;
import com.example.promonade.models.Product;
import com.example.promonade.models.Service;

public class Product_ServiceTransformer {

    public static Product productRequestToProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
    }

    public static Service serviceRequestToService(ServiceRequest serviceRequest){
        return Service.builder()
                .name(serviceRequest.getName())
                .price(serviceRequest.getPrice())
                .description(serviceRequest.getDescription())
                .build();
    }
}
