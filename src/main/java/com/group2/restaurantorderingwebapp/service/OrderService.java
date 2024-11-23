package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService  {



    OrderResponse createOrder(OrderRequest orderRequest);

    String updateOrderStatus(Long id);

    String updateOrderUser(Long id, Long userId);

    OrderResponse getOrderById(Long id);

    PageCustom<OrderResponse> getAllOrders(Pageable pageable);

    String deleteOrder(Long id);


    PageCustom<OrderResponse> getOrdersByPosition(Long positionId, Pageable pageable);

    PageCustom<OrderResponse> getOrdersByUser(Long userId, Pageable pageable);
}
