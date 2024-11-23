package com.group2.restaurantorderingwebapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int quantity;
    private Long userId;
    private Long dishId;
    private Long positionId;

}
