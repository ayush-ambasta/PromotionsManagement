package com.example.promonade.service;

import com.example.promonade.dto.request.customerdtos.BehaviourRequest;
import com.example.promonade.dto.request.customerdtos.DemographyRequest;
import com.example.promonade.dto.request.customerdtos.PurchaseRequest;
import com.example.promonade.exceptions.customerExceptions.CustomerNotFoundException;
import com.example.promonade.exceptions.customerExceptions.PurchasedProductsandServicesNotFound;
import com.example.promonade.models.*;
import com.example.promonade.repositories.BehaviourRepository;
import com.example.promonade.repositories.CustomerRepository;
import com.example.promonade.repositories.DemographyRepository;
import com.example.promonade.repositories.PurchaseRepository;
import com.example.promonade.service.transformers.BehaviourTransformer;
import com.example.promonade.service.transformers.DemographyTransformer;
import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CustomerService {

    private  final CustomerRepository customerRepository;
    private final BehaviourRepository behaviourRepository;
    private final DemographyRepository demographyRepository;
    private final ProductService productService;
    private final ServiceService serviceService;
    private final PromotionsService promotionsService;
    private final PurchaseRepository purchaseRepository;
    public Customer addCustomer() {
        Customer customer = new Customer();
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        customerRepository.delete(customerOptional.get());
    }


    public Behaviour addBehaviour(BehaviourRequest behaviourRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(behaviourRequest.getCustomerId());
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        Behaviour behaviour = BehaviourTransformer.behaviourRequestToBehaviour(behaviourRequest);
        Behaviour savedBehaviour = behaviourRepository.save(behaviour);
        Customer customer = customerOptional.get();
        //need to save both in behaviour and customer repository for accurate results
        customer.getBehaviourList().add(savedBehaviour);
        customerRepository.save(customer);
        return savedBehaviour;
    }

    public Demographics addDemographics(DemographyRequest demographyRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(demographyRequest.getCustomerId());
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        Demographics demographics = DemographyTransformer.demographyRequestToDemographics(demographyRequest);
        Demographics savedDemographics = demographyRepository.save(demographics);
        Customer customer = customerOptional.get();
        customer.setDemographics(demographics);
        customerRepository.save(customer);
        return savedDemographics;
    }

    public Purchase addPurchase(PurchaseRequest purchaseRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(purchaseRequest.getCustomerId());
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        Customer customer = customerOptional.get();
        List<Integer> productIdList = purchaseRequest.getProductIdList();
        List<Integer> serviceIdList = purchaseRequest.getServiceIdList();
        if(productIdList == null && serviceIdList ==null)
        {
          throw new PurchasedProductsandServicesNotFound("No products and services selected to buy");
        }

        Purchase purchase = new Purchase();
        double amountSpent = 0;
        if(productIdList !=null) {
            for (int productId : productIdList) {
                Product product = productService.getProductById(productId);
                purchase.getProductList().add(product);
                amountSpent += product.getPrice();
            }
        }
        if(serviceIdList !=null) {
            for (int serviceId : serviceIdList) {
                Service service = serviceService.getServiceById(serviceId);
                purchase.getServiceList().add(service);
                amountSpent += service.getPrice();
            }
        }
        purchase.setAmountSpent(amountSpent);
        if(purchaseRequest.getPromotionId() > 0)
        {
            purchase.setPromotionUsed(promotionsService.getPromotion(purchaseRequest.getPromotionId()));
        }
        purchase.setTimeOfPurchase(purchaseRequest.getTimeOfPurchase());
        Purchase savedPurchase = purchaseRepository.save(purchase);
        customer.getPurchaseList().add(savedPurchase);
        customerRepository.save(customer);
        return savedPurchase;

    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty())
        {
            throw new CustomerNotFoundException("No customer found having the given Id");
        }
        return customerOptional.get();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
