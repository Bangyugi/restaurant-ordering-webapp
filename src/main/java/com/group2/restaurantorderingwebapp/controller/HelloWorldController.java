package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping()
    public ResponseEntity<ApiResponse> sayHello(){
        ApiResponse apiResponse = ApiResponse.success("Hello world \nVersion: 0.1.0 RELEASE \n Message: update CORS API");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
