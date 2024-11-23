package com.group2.restaurantorderingwebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private int quantity;
    private Long timeServing;
    private Double totalPrice;
    private boolean status;
    private Long userId;
    private Set<OrderItemResponse> orderItems;
    
}
