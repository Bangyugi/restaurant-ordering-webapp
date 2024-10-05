package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.CategoryRequest;
import com.group2.restaurantorderingwebapp.dto.response.CategoryDishResponse;
import com.group2.restaurantorderingwebapp.dto.response.DishResponse;
import com.group2.restaurantorderingwebapp.entity.Category;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.CategoryRepository;
import com.group2.restaurantorderingwebapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDishResponse addCategory(CategoryRequest categoryRequest){
        Category category = modelMapper.map(categoryRequest,Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDishResponse.class);
    }

    @Override
    public CategoryDishResponse getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        CategoryDishResponse categoryDishResponse = modelMapper.map(category, CategoryDishResponse.class);
        categoryDishResponse.setDishes(category.getDishes().stream().map(result->modelMapper.map(result, DishResponse.class)).collect(Collectors.toSet()));
        return categoryDishResponse;
    }

    @Override
    public List<CategoryDishResponse> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDishResponse> categoryDishResponses = new ArrayList<>();
        for(Category category:categories){
            CategoryDishResponse categoryDishResponse = modelMapper.map(category, CategoryDishResponse.class);
            categoryDishResponse.setDishes(category.getDishes().stream().map(result->modelMapper.map(result, DishResponse.class)).collect(Collectors.toSet()));
            categoryDishResponses.add(categoryDishResponse);
        }
        return  categoryDishResponses;
    }

    @Override
    public CategoryDishResponse getCategoryByCategoryName(String categoryName){
        Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(()->new ResourceNotFoundException("Category","name",categoryName));
        CategoryDishResponse categoryDishResponse = modelMapper.map(category, CategoryDishResponse.class);
        categoryDishResponse.setDishes(category.getDishes().stream().map(result->modelMapper.map(result, DishResponse.class)).collect(Collectors.toSet()));
        return categoryDishResponse;
    }

    @Override
    public CategoryDishResponse updateCategory(CategoryRequest categoryRequest, Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("category","id",id));
        modelMapper.map(categoryRequest,category);
        return modelMapper.map(categoryRepository.save(category), CategoryDishResponse.class);
    }

    @Override
    public String deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(category);
        return "Category with id: " +id+ " was deleted successfully";
    }
}
