package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.CategoryRequest;
import com.group2.restaurantorderingwebapp.dto.response.CategoryDishResponse;

import java.util.List;

public interface CategoryService {
    CategoryDishResponse addCategory (CategoryRequest categoryRequest);

    CategoryDishResponse getCategoryById(Long id);

    List<CategoryDishResponse> getAllCategory();

    CategoryDishResponse getCategoryByCategoryName(String categoryName);

    CategoryDishResponse updateCategory(CategoryRequest categoryRequest, Long id);


    String deleteCategory(Long id);
}
