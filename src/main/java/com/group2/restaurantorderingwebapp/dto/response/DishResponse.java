package com.group2.restaurantorderingwebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishResponse {
    private Long id;
    private String dishName;
    private String image;
    private String description;
    private Double price;
    private String status;
    private String ingredient;
    private String portion;
    private Long cookingTime;
}
