package com.group2.restaurantorderingwebapp.dto.response;

import com.group2.restaurantorderingwebapp.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponse {
    private String id;
    private String name;
    private Set<OrderResponse> orders;
}
