package com.example.promonade.controllers;

import com.example.promonade.dto.request.customerdtos.BehaviourRequest;
import com.example.promonade.dto.request.customerdtos.DemographyRequest;
import com.example.promonade.dto.request.customerdtos.PurchaseRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.models.Behaviour;
import com.example.promonade.models.Customer;
import com.example.promonade.models.Demographics;
import com.example.promonade.models.Purchase;
import com.example.promonade.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer addCustomer()
    {
       return customerService.addCustomer();
    }

    @GetMapping("/all")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/behaviour")
    public Behaviour addBehaviour(@RequestBody BehaviourRequest behaviourRequest)
    {
        return customerService.addBehaviour(behaviourRequest);
    }

    @PostMapping("/demographics")
    public Demographics addDemographics(@RequestBody DemographyRequest demographyRequest)
    {
        return customerService.addDemographics(demographyRequest);
    }

    @PostMapping("/purchase")
    public Purchase addPurchase(@RequestBody PurchaseRequest purchaseRequest)
    {
        return customerService.addPurchase(purchaseRequest);
    }

    @DeleteMapping
    public  ResponseEntity<UpdationResponse> deleteCustomer(@RequestParam("id") Long id)
    {
        customerService.deleteCustomer(id);
        UpdationResponse updationResponse = new UpdationResponse("Customer is deleted",true);
        return new ResponseEntity<>(updationResponse,HttpStatus.ACCEPTED);
    }


    @GetMapping
    public Customer getCustomerById(@RequestParam("id") Long id)
    {
        return customerService.getCustomerById(id);
    }
}
