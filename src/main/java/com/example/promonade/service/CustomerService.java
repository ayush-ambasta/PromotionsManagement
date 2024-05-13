package com.example.promonade.service;

import com.example.promonade.dto.response.CustomerResponse;
import com.example.promonade.exceptions.customerExceptions.CustomerNotFoundException;
import com.example.promonade.models.Customer;
import com.example.promonade.repositories.CustomerRepository;
import com.example.promonade.service.transformers.CustomerTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private  final CustomerRepository customerRepository;
    public CustomerResponse addCustomer() {
        Customer customer = new Customer();
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        customerRepository.delete(customerOptional.get());
    }


}
