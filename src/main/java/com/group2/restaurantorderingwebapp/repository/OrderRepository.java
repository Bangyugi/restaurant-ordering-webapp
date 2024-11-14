package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
