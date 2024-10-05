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
public class CategoryDishResponse {
    private Long id;
    private String categoryName;
    private String description;
    private Set<DishResponse> dishes;
}
