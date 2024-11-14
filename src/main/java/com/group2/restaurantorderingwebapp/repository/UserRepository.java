package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
