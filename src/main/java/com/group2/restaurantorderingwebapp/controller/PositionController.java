package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.PositionRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.PositionResponse;
import com.group2.restaurantorderingwebapp.service.PositionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Tag(name = "Positions")
public class PositionController {

    private  final PositionService positionService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addPosition(@RequestBody PositionRequest positionRequest) {
        ApiResponse apiResponse = ApiResponse.success(positionService.createPosition(positionRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

@GetMapping("/{positionId}")
public ResponseEntity<ApiResponse> getById(@PathVariable("positionId") Long positionId) {
    PositionResponse positionResponse = positionService.getPosition(positionId);
    ApiResponse apiResponse = ApiResponse.success(positionResponse);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@GetMapping
public ResponseEntity<ApiResponse> getAll() {
    List<PositionResponse> positionResponses = positionService.getAllPositions();
    ApiResponse apiResponse = ApiResponse.success(positionResponses);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@PutMapping("/{positionId}")
public ResponseEntity<ApiResponse> updateById(@PathVariable("positionId") Long positionId, @RequestBody PositionRequest positionRequest) {
    PositionResponse positionResponse = positionService.updatePosition(positionId, positionRequest);
    ApiResponse apiResponse = ApiResponse.success(positionResponse);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}

@DeleteMapping("/{positionId}")
public ResponseEntity<ApiResponse> deleteById(@PathVariable("positionId") Long positionId) {
    ApiResponse apiResponse = ApiResponse.success(positionService.deletePosition(positionId));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}
}
