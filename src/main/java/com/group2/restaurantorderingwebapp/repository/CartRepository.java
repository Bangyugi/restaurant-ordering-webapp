package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

}
