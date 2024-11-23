package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
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

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long orderId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrderById(orderId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/update-status")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Long orderId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.updateOrderStatus(orderId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/update-user")
    public ResponseEntity<ApiResponse> updateOrderUser(@PathVariable Long orderId, @RequestParam("user") Long userId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.updateOrderUser(orderId, userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.deleteOrder(orderId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
