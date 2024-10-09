package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.DishRequest;
import com.group2.restaurantorderingwebapp.dto.response.DishCategoryResponse;
import com.group2.restaurantorderingwebapp.dto.response.DishResponse;

import java.util.List;

public interface DishService {
    DishCategoryResponse addDish(DishRequest dishRequest);

    DishCategoryResponse getDishById(Long id);

    List<DishCategoryResponse> getAllDish();

    DishCategoryResponse updateDish(Long id, DishRequest dishRequest);

    String deleteDish(Long id);

}
