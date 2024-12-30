package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Cart;
import com.group2.restaurantorderingwebapp.entity.Reservation;
import com.group2.restaurantorderingwebapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {


    Page<Reservation> findAllByUser(User user, Pageable pageable);
}
