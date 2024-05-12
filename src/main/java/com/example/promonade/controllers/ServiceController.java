package com.example.promonade.controllers;


import com.example.promonade.dto.response.ServiceResponseDTO;
import com.example.promonade.models.Service;
import com.example.promonade.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public ResponseEntity<com.example.promonade.dto.response.ServiceResponseDTO> addService(@RequestBody com.example.promonade.dto.response.ServiceResponseDTO serviceRequestDTO) {
        Service service = new Service(serviceRequestDTO.getName(), serviceRequestDTO.getDescription(), serviceRequestDTO.getPrice());
        Service addedService = serviceService.addService(service);
        com.example.promonade.dto.response.ServiceResponseDTO responseDTO = new com.example.promonade.dto.response.ServiceResponseDTO(addedService.getId(), addedService.getName(), addedService.getDescription(), addedService.getPrice());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<com.example.promonade.dto.response.ServiceResponseDTO> updateService(@PathVariable Long serviceId, @RequestBody com.example.promonade.dto.response.ServiceResponseDTO serviceRequestDTO) {
        Service service = new Service(serviceRequestDTO.getName(), serviceRequestDTO.getDescription(), serviceRequestDTO.getPrice());
        ServiceResponseDTO updatedService = serviceService.updateService(serviceId, service);
        com.example.promonade.dto.response.ServiceResponseDTO responseDTO = new com.example.promonade.dto.response.ServiceResponseDTO(updatedService.getId(), updatedService.getName(), updatedService.getDescription(), updatedService.getPrice());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
