package com.example.promonade.repositories;

import com.example.promonade.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
