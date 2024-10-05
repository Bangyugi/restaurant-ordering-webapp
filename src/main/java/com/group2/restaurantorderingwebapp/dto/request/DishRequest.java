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
    private String price;
    private String status;
    private Set<String> categories;
}
