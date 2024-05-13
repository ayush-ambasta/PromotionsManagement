package com.example.promonade.service;

import com.example.promonade.dto.request.productservicedtos.ServiceRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.exceptions.productServiceExceptions.ServiceIncompleteException;
import com.example.promonade.exceptions.productServiceExceptions.ServiceNotFoundException;
import com.example.promonade.models.Service;
import com.example.promonade.repositories.ServiceRepository;
import com.example.promonade.service.transformers.Product_ServiceTransformer;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Service addService(ServiceRequest serviceRequest) {
        if(serviceRequest.getName()==null){
            throw new ServiceIncompleteException("Product name cannot be empty");
        }
        if(serviceRequest.getDescription()==null){
            throw new ServiceIncompleteException("Product description cannot be empty");
        }
        if(serviceRequest.getPrice()==null){
            throw new ServiceIncompleteException("Product price cannot be empty");
        }
        Service service = Product_ServiceTransformer.serviceRequestToService(serviceRequest);
        return serviceRepository.save(service);
    }

    public UpdationResponse deleteService(int serviceId) {
        Optional<Service> serviceOptional = serviceRepository.findById(serviceId);
        if(serviceOptional.isEmpty()){
            throw new ServiceNotFoundException("Service not found for id " +serviceId);
        }
        Service service = serviceOptional.get();
        serviceRepository.delete(service);
        return new UpdationResponse(String.format("Service %s with id %d is successfully deleted", service.getName(), serviceId), true);
    }

    public Service updateService(int serviceId, ServiceRequest serviceRequest) {
        Optional<Service> serviceOptional = serviceRepository.findById(serviceId);
        if(serviceOptional.isEmpty()){
            throw new ServiceNotFoundException("Service not found for id " +serviceId);
        }
        Service existingService = serviceOptional.get();

        existingService.setName(serviceRequest.getName());
        existingService.setDescription(serviceRequest.getDescription());
        existingService.setPrice(serviceRequest.getPrice());

        return serviceRepository.save(existingService);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(int serviceId) {
        Optional<Service> serviceOptional = serviceRepository.findById(serviceId);
        if(serviceOptional.isEmpty()){
            throw new ServiceNotFoundException("Service not found for id " +serviceId);
        }
        return serviceOptional.get();
    }
}

