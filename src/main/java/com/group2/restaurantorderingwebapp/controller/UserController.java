package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService useService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserResponse userResponse){
        ApiResponse apiResponse = ApiResponse.success(useService.createUser(userResponse));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUser(Pageable pageable){
        ApiResponse apiResponse = ApiResponse.success(useService.getAllUser(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(useService.getUserById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserResponse userResponse){
        ApiResponse apiResponse = ApiResponse.success(useService.updateUser(id, userResponse));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(useService.deleteUser(id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
