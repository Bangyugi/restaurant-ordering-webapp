package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.DishRequest;
import com.group2.restaurantorderingwebapp.dto.response.DishResponse;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.entity.Category;
import com.group2.restaurantorderingwebapp.entity.Dish;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.CategoryRepository;
import com.group2.restaurantorderingwebapp.repository.DishRepository;
import com.group2.restaurantorderingwebapp.service.DishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public DishResponse addDish(DishRequest dishRequest) {
        if (dishRepository.existsByDishName(dishRequest.getDishName())) {
            throw new AppException(ErrorCode.DISH_EXISTED);
        }
        Dish dish = modelMapper.map(dishRequest,Dish.class);
        Set<Category> categories = new HashSet<>();
        for (String categoryName : dishRequest.getCategories()) {
            Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
            categories.add(category);
        }
        dish.setCategories(categories);
        dish = dishRepository.save(dish);
        return modelMapper.map(dish,DishResponse.class);
    }

    @Override
    public PageCustom<DishResponse> getAllDishes(Pageable pageable) {
        Page<Dish> page = dishRepository.findAll(pageable);
        PageCustom<DishResponse> pageCustom = PageCustom.<DishResponse>builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageContent(page.getContent().stream().map(dish->modelMapper.map(dish, DishResponse.class)).toList())
                .build();

        return pageCustom;
    }

    @Override
    public DishResponse getDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        return modelMapper.map(dish, DishResponse.class);
    }

    @Override
    public DishResponse updateDish(Long dishId, DishRequest dishRequest) {
        if (dishRepository.existsByDishName(dishRequest.getDishName())) {
            throw new AppException(ErrorCode.DISH_EXISTED);
        }
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        modelMapper.map(dishRequest,dish);
        Set<Category> categories = new HashSet<>();
        for (String categoryName : dishRequest.getCategories()) {
            Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
            categories.add(category);
        }
        dish.setCategories(categories);
        dish = dishRepository.save(dish);
        return modelMapper.map(dish,DishResponse.class);
    }

    @Override
    public String deleteDish(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        dishRepository.delete(dish);
        return "Dish with id: " +dishId+ " was deleted successfully";
    }

    @Override
    public List<DishResponse> getDishesByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        List<Dish> dishes = dishRepository.findAllByCategories(category);
        return dishes.stream().map(dish -> modelMapper.map(dish, DishResponse.class)).collect(Collectors.toList());
    }
}


