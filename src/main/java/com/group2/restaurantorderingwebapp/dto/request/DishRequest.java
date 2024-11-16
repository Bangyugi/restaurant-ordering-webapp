package com.group2.restaurantorderingwebapp.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {
    private String dishName;
    private String image;
    private String description;
    private Double price;
    private String status;
    private String ingredient;
    private String portion;
    private Long cookingTime;
    private Set<String> categories;
}
