package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addOrder(@RequestBody OrderRequest orderRequest) {
        ApiResponse apiResponse = ApiResponse.success(orderService.createOrder(orderRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllOrder() {
        ApiResponse apiResponse = ApiResponse.success(orderService.getAllOrders());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrderById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.success(orderService.updateOrderStatus(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/update-user")
    public ResponseEntity<ApiResponse> updateOrderUser(@PathVariable Long id, @RequestParam("user") Long userId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.updateOrderUser(id, userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.success(orderService.deleteOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
