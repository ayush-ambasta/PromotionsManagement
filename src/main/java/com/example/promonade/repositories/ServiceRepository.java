package com.example.promonade.repositories;

import com.example.promonade.models.Service;
import java.util.Optional;

public interface ServiceRepository {

    Optional<Service> findById(Long id);

    void deleteById(Long serviceId);

    Service save(Service existingService);
}

