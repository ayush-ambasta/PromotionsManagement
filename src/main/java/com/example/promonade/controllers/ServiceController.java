package com.example.promonade.controllers;


import com.example.promonade.dto.request.productservicedtos.ServiceRequest;
import com.example.promonade.models.Product;
import com.example.promonade.models.Service;
import com.example.promonade.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok(serviceService.addService(serviceRequest));
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable("serviceId") int serviceId) {
        return ResponseEntity.ok(serviceService.deleteService(serviceId));
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<?> updateService(@PathVariable("serviceId") int serviceId, @RequestBody ServiceRequest serviceRequest) {
        Service updatedService = serviceService.updateService(serviceId, serviceRequest);
        return ResponseEntity.ok(updatedService);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getService(@PathVariable("serviceId") int serviceId){
        return ResponseEntity.ok(serviceService.getServiceById(serviceId));
    }

    @GetMapping()
    public ResponseEntity<?> getAllServices(){
        return ResponseEntity.ok(serviceService.getAllServices());
    }
}
