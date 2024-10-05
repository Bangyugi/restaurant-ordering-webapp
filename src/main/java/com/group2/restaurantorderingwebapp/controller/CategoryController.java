package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.CategoryRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addCategory (@Valid @RequestBody CategoryRequest categoryRequest){
        ApiResponse apiResponse = ApiResponse.success(categoryService.addCategory(categoryRequest));
        apiResponse.setCode(201);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
//        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryById(id));
//        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
//    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory(){
        ApiResponse apiResponse = ApiResponse.success(categoryService.getAllCategory());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest){
        ApiResponse apiResponse = ApiResponse.success(categoryService.updateCategory(categoryRequest,id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(categoryService.deleteCategory(id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable String categoryName){
        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryByCategoryName(categoryName));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
