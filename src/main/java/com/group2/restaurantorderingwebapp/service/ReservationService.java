package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.CategoryRequest;
import com.group2.restaurantorderingwebapp.dto.request.ReservationRequest;
import com.group2.restaurantorderingwebapp.dto.response.CategoryResponse;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.dto.response.ReservationResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    ReservationResponse create(ReservationRequest reservationRequest);

    String deleteById(Long reservationId);

    PageCustom<ReservationResponse> getReservationByUser(Long userId, Pageable pageable);

    // User confirm make a reservation
    String confirmReservationStatus(Long id);

    //Manger update status, neu truyen vao status 2 la dat  don thanh cong, 3 la da thanh toan Kien nha
    String updateStatus(Long id, int status);

    String cancelReservation(Long id, LocalDateTime orderTime);
}
