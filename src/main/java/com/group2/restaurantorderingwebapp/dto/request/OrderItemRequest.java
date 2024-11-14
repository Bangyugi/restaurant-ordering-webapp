package com.group2.restaurantorderingwebapp.dto.request;

import com.group2.restaurantorderingwebapp.entity.Dish;
import com.group2.restaurantorderingwebapp.entity.Order;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private int quantity;
    private Long orderId;
    private Long dishId;
}
