package com.group2.restaurantorderingwebapp.dto.response;

import com.group2.restaurantorderingwebapp.entity.OrderItem;
import com.group2.restaurantorderingwebapp.entity.Position;
import com.group2.restaurantorderingwebapp.entity.User;
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
    private Long id;
    private int quantity;
    private Long timeServing;
    private Double totalPrice;
    private boolean status;
    private Long userId;
    private Set<OrderItemResponse> orderItems;
    
}
