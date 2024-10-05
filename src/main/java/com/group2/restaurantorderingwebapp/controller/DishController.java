package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.DishRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addDish(@Valid @RequestBody DishRequest dishRequest){
        ApiResponse apiResponse = ApiResponse.success(dishService.addDish(dishRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllDish(){
        ApiResponse apiResponse = ApiResponse.success(dishService.getAllDish());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDishById(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(dishService.getDishById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDish(@PathVariable Long id, @Valid @RequestBody DishRequest dishRequest){
        ApiResponse apiResponse = ApiResponse.success(dishService.updateDish(id,dishRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDish (@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(dishService.deleteDish(id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
