package com.backend.promotionsmanagement.repositories;


import com.backend.promotionsmanagement.enums.ERole;
import com.backend.promotionsmanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
