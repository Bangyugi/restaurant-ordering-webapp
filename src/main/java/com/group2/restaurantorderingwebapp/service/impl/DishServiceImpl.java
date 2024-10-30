package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.DishRequest;
import com.group2.restaurantorderingwebapp.dto.response.CategoryResponse;
import com.group2.restaurantorderingwebapp.dto.response.DishCategoryResponse;
import com.group2.restaurantorderingwebapp.dto.response.DishResponse;
import com.group2.restaurantorderingwebapp.entity.Category;
import com.group2.restaurantorderingwebapp.entity.Dish;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.CategoryRepository;
import com.group2.restaurantorderingwebapp.repository.DishRepository;
import com.group2.restaurantorderingwebapp.service.DishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public DishCategoryResponse addDish(DishRequest dishRequest){
        Dish dish = modelMapper.map(dishRequest,Dish.class);
        Set<Category> categories = new HashSet<>();
        for (String categoryName : dishRequest.getCategories()){
            Category category =  categoryRepository.findByCategoryName(categoryName).orElseThrow(()-> new ResourceNotFoundException("category","name",categoryName));
            categories.add(category);
        }
        dish.setCategories(categories);
        return modelMapper.map(dishRepository.save(dish), DishCategoryResponse.class);
    }

    @Override
    public DishCategoryResponse getDishById(Long id){
        Dish dish = dishRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Dish","id",id));
        DishCategoryResponse dishCategoryResponse = modelMapper.map(dish, DishCategoryResponse.class);
        dishCategoryResponse.setCategories(dish.getCategories().stream().map(result->modelMapper.map(result, CategoryResponse.class)).collect(Collectors.toSet()));
        return dishCategoryResponse;
    }

    @Override
    public List<DishCategoryResponse> getAllDish(){
       List<Dish> dishes = dishRepository.findAllByOrderByDishNameAsc();
       List<DishCategoryResponse> dishCategoryResponses = new ArrayList<>();
       for(Dish dish: dishes){
           DishCategoryResponse dishCategoryResponse = modelMapper.map(dish, DishCategoryResponse.class);
           dishCategoryResponse.setCategories(dish.getCategories().stream().map(result->modelMapper.map(result, CategoryResponse.class)).collect(Collectors.toSet()));
           dishCategoryResponses.add(dishCategoryResponse);
       }
       return dishCategoryResponses;
    }

    @Override
    public DishCategoryResponse updateDish(Long id, DishRequest dishRequest){
        Dish dish = dishRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Dish","id",id));
        Set<Category> categories = new HashSet<>();
        for (String categoryName: dishRequest.getCategories())
        {
            Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(()->new ResourceNotFoundException("category","name",categoryName));
            categories.add(category);
        }
        modelMapper.map(dishRequest,dish);
        dish.setCategories(categories);
        return modelMapper.map(dishRepository.save(dish), DishCategoryResponse.class);
    }

    @Override
    public String deleteDish(Long id){
        Dish dish = dishRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Dish","id",id));
        dishRepository.delete(dish);
        return "Dish with id: " +id+ " was deleted successfully";
    }


}


