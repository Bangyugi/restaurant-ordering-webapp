package com.group2.restaurantorderingwebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private int status;
    private Double totalAmount;
    private String orderStatus;
    private boolean ratingStatus;
    private UserResponse user;
    private OrderResponse order;
    private PositionResponse position;
}
