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
import com.group2.restaurantorderingwebapp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final RedisService redisService;
    private final String KEY = "dish";

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
        redisService.deleteAll(KEY);
        return modelMapper.map(dish,DishResponse.class);
    }

    @Override
    public PageCustom<DishResponse> getAllDishes(Pageable pageable) {
        String field = "allDishes" +pageable.toString();
        var json  = redisService.getHash(KEY,field);
        if (json == null){

        Page<Dish> page = dishRepository.findAll(pageable);
        PageCustom<DishResponse> pageCustom = PageCustom.<DishResponse>builder()
                .pageNo(page.getNumber()+1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageContent(page.getContent().stream().map(dish->modelMapper.map(dish, DishResponse.class)).toList())
                .build();
        redisService.setHashRedis(KEY,field,redisService.convertToJson(pageCustom));
        return pageCustom;

        }
        // Chuyển JSON thành object
        return (PageCustom<DishResponse>) redisService.convertToObject((String) json, PageCustom.class);
    }

    @Override
    public DishResponse getDishById(Long dishId) {
        String field = "dishById:"+dishId;
        var json  = redisService.getHash(KEY,field);
        if (json == null){

        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        DishResponse dishResponse = modelMapper.map(dish, DishResponse.class);
        redisService.setHashRedis(KEY,field,redisService.convertToJson(dishResponse));
        return dishResponse;
        }
        return redisService.convertToObject((String) json, DishResponse.class);

    }

    @Override
    public DishResponse updateDish(Long dishId, DishRequest dishRequest) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        modelMapper.map(dishRequest,dish);
        Set<Category> categories = new HashSet<>();
        for (String categoryName : dishRequest.getCategories()) {
            Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
            categories.add(category);
        }
        dish.setCategories(categories);
        dish = dishRepository.save(dish);
        redisService.deleteAll(KEY);
        return modelMapper.map(dish,DishResponse.class);
    }

    @Override
    public String deleteDish(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
        dishRepository.delete(dish);
        redisService.deleteAll(KEY);
        return "Dish with id: " +dishId+ " was deleted successfully";
    }

    @Override
    public List<DishResponse> getDishesByCategory(String categoryName) {
        String field = "DishByCategoryName:" + categoryName;
        var json  = redisService.getHash(KEY,field);
        if (json == null){

        Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        List<Dish> dishes = dishRepository.findAllByCategories(category);
        List<DishResponse> dishResponses =  dishes.stream().map(dish -> modelMapper.map(dish, DishResponse.class)).toList();
        redisService.setHashRedis(KEY, field,redisService.convertToJson(dishResponses));
        return dishResponses;
        }
        return (List<DishResponse>) redisService.convertToObject((String) json, List.class);

    }

    @Override
    public PageCustom<DishResponse> searchDish(String dishName, Pageable pageable) {
        String field = "searchDish:" + dishName + pageable.toString();
        var json = redisService.getHash(KEY, field);
        if (json == null) {
            Page<Dish> page = dishRepository.findAllByDishNameContainingIgnoreCase(dishName, pageable);
            PageCustom<DishResponse> pageCustom = PageCustom.<DishResponse>builder()
                    .pageNo(page.getNumber()+1)
                    .pageSize(page.getSize())
                    .totalPages(page.getTotalPages())
                    .pageContent(page.getContent().stream().map(dish -> modelMapper.map(dish, DishResponse.class)).toList())
                    .build();
            redisService.setHashRedis(KEY, field, redisService.convertToJson(pageCustom));
            return pageCustom;
        }
        return (PageCustom<DishResponse>) redisService.convertToObject((String) json, PageCustom.class);
    }
}


