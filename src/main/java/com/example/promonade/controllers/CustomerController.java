package com.example.promonade.controllers;

import com.example.promonade.dto.response.CustomerResponse;
import com.example.promonade.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer()
    {
       return new ResponseEntity<>(customerService.addCustomer(), HttpStatus.CREATED);
    }

    @DeleteMapping
    public  ResponseEntity<String> deleteCustomer(@RequestParam("id") Long id)
    {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer is deleted",HttpStatus.ACCEPTED);
    }
}
