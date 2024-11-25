package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ApiResponse> getAllOrder(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "createAt", required = false) String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).ascending() );
        ApiResponse apiResponse = ApiResponse.success(orderService.getAllOrders(pageable));
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable("userId") Long userId,
                                                        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                        @RequestParam(value = "sortBy",defaultValue = "createAt", required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).ascending() );
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrdersByUser(userId,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<ApiResponse> getOrderByPositionId(@PathVariable("positionId") Long positionId,
                                                            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                            @RequestParam(value = "sortBy",defaultValue = "createAt", required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).ascending() );
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrdersByPosition(positionId,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
