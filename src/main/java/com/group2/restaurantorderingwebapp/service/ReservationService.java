package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.ReservationRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest reservationRequest);
    String deleteOrder(Long id);

}
