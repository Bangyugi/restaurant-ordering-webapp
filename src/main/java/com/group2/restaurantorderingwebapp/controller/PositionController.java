package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.PositionRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.PositionResponse;
import com.group2.restaurantorderingwebapp.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private  final PositionService positionService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addPosition(@RequestBody PositionRequest positionRequest) {
        ApiResponse apiResponse = ApiResponse.success(positionService.createPosition(positionRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

@GetMapping("/{id}")
public ResponseEntity<ApiResponse> getById(@PathVariable String id) {
    PositionResponse positionResponse = positionService.getPosition(id);
    ApiResponse apiResponse = ApiResponse.success(positionResponse);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@GetMapping
public ResponseEntity<ApiResponse> getAll() {
    List<PositionResponse> positionResponses = positionService.getAllPositions();
    ApiResponse apiResponse = ApiResponse.success(positionResponses);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@PutMapping("/{id}")
public ResponseEntity<ApiResponse> updateById(@PathVariable String id, @RequestBody PositionRequest positionRequest) {
    PositionResponse positionResponse = positionService.updatePosition(id, positionRequest);
    ApiResponse apiResponse = ApiResponse.success(positionResponse);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse> deleteById(@PathVariable String id) {
    ApiResponse apiResponse = ApiResponse.success(positionService.deletePosition(id));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}
}
