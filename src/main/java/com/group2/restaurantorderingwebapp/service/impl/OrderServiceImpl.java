package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.response.OrderItemResponse;
import com.group2.restaurantorderingwebapp.entity.*;
import com.group2.restaurantorderingwebapp.repository.DishRepository;
import com.group2.restaurantorderingwebapp.repository.OrderRepository;
import com.group2.restaurantorderingwebapp.repository.PositonRepository;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final PositonRepository positonRepository;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        if (orderRequest.getUserId() != null)
        {
            User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", orderRequest.getUserId()));
            order.setUser(user);
        }
        Position position =positonRepository.findById(orderRequest.getPositionId()).orElseThrow(() -> new ResourceNotFoundException("Position", "id", orderRequest.getPositionId()));
        order.setPosition(position);
        order.setStatus(false);
        Set<OrderItem> orderItems = orderRequest.getDishQuantities().entrySet().stream()
                .map(entry -> {
                    Dish dish = dishRepository.findById(entry.getKey()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", entry.getKey()));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDish(dish);
                    orderItem.setQuantity(entry.getValue());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet());
        Double totalPrice = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getDish().getPrice() * orderItem.getQuantity())
                .sum();
        Double timeServing = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getDish().getCookingTime() * orderItem.getQuantity())
                .sum();
        order.setTimeServing(Math.round(timeServing));
        order.setOrderItems(orderItems);
        order.setTotalPrice(Math.ceil(totalPrice*100.0)/100.0);
        orderRepository.save(order);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).collect(Collectors.toSet()));
        if (order.getUser() != null)
        {
            orderResponse.setUserId(orderRequest.getUserId());
        }

        return orderResponse;
    }


    @Override
    public OrderResponse updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(true);
        orderRepository.save(order);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).collect(Collectors.toSet()));
        if (order.getUser() != null)
        {
            orderResponse.setUserId(order.getUser().getId());
        }
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrderUser(Long id, Long userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        order.setUser(user);
        orderRepository.save(order);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).collect(Collectors.toSet()));
        if (order.getUser() != null)
        {
            orderResponse.setUserId(order.getUser().getId());
        }
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).collect(Collectors.toSet()));
        if (order.getUser() != null)
        {
            orderResponse.setUserId(order.getUser().getId());
        }
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                    orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).collect(Collectors.toSet()));
                    if (order.getUser() != null)
                    {
                        orderResponse.setUserId(order.getUser().getId());
                    }
                    return orderResponse;
                })
                .collect(Collectors.toList());

    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
        return "Deleted order successfully";
    }


}
