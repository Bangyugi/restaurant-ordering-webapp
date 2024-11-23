package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.CategoryRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;



    @PostMapping()
    public ResponseEntity<ApiResponse> addCategory (@Valid @RequestBody CategoryRequest categoryRequest){
        ApiResponse apiResponse = ApiResponse.success(categoryService.addCategory(categoryRequest));
        apiResponse.setCode(201);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryById(categoryId));
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory(){
        ApiResponse apiResponse = ApiResponse.success(categoryService.getAllCategory());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryRequest categoryRequest){
        ApiResponse apiResponse = ApiResponse.success(categoryService.updateCategory(categoryId, categoryRequest));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        ApiResponse apiResponse = ApiResponse.success(categoryService.deleteCategory(categoryId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable String categoryName){
        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryByCategoryName(categoryName));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
