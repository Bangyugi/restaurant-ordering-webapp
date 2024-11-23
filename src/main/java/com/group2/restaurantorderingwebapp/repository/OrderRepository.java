package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Order;
import com.group2.restaurantorderingwebapp.entity.Position;
import com.group2.restaurantorderingwebapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findAllByPosition(Position position, Pageable pageable);

    Page<Order> findAllByUser(User user, Pageable pageable);
}
