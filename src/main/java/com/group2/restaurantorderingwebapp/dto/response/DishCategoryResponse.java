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
public class DishCategoryResponse {
    private Long id;
    private String dishName;
    private String image;
    private String description;
    private String price;
    private String status;
    private String ingredient;
    private String portion;
    private String cookingTime;
    private Set<CategoryResponse> categories;
}
