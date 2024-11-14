package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService  {


    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse updateOrderStatus(Long id);

    OrderResponse updateOrderUser(Long id, Long userId);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    String deleteOrder(Long id);
}
