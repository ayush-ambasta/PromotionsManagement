package com.example.promonade.repositories;

import com.example.promonade.models.Demographics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemographyRepository extends JpaRepository<Demographics, Long> {
}
