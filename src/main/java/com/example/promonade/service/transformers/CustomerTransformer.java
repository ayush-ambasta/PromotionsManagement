package com.example.promonade.service.transformers;

import com.example.promonade.dto.response.CustomerResponse;
import com.example.promonade.models.Customer;

public class CustomerTransformer {

    public static CustomerResponse customerToCustomerResponse (Customer customer)
    {
        return CustomerResponse.builder()
                .Id(customer.getId())
                .build();
    }
}
