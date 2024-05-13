package com.example.promonade.repositories;

import com.example.promonade.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

}

