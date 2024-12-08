package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.response.*;
import com.group2.restaurantorderingwebapp.entity.*;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.repository.DishRepository;
import com.group2.restaurantorderingwebapp.repository.OrderRepository;
import com.group2.restaurantorderingwebapp.repository.PositonRepository;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.OrderService;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final PositonRepository positonRepository;

    private final UserService userService;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        if (orderRepository.existsByDishAndPositionAndStatus(orderRequest.getDishId(),orderRequest.getPositionId(),false))
        {
            Order existingOrder = orderRepository.findByDishAndPositionAndStatus(orderRequest.getDishId(),orderRequest.getPositionId(),false).orElseThrow(()->new AppException(ErrorCode.ORDER_NOT_EXISTED));
            existingOrder.setQuantity(existingOrder.getQuantity()+orderRequest.getQuantity());
            existingOrder.setTotalPrice(existingOrder.getTotalPrice()+orderRequest.getQuantity()*existingOrder.getDish().getPrice());
            existingOrder.setTimeServing(existingOrder.getTimeServing()+orderRequest.getQuantity()*existingOrder.getDish().getCookingTime());
            orderRepository.save(existingOrder);
            return modelMapper.map(existingOrder, OrderResponse.class);
        }

        Position position =positonRepository.findById(orderRequest.getPositionId()).orElseThrow(() -> new ResourceNotFoundException("Position", "id", orderRequest.getPositionId()));
        Dish dish = dishRepository.findById(orderRequest.getDishId()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", orderRequest.getDishId()));
        Order order = new Order();
        User user = new User();
        if (orderRequest.getUserId() != null)
        {
            user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", orderRequest.getUserId()));
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            user = userService.createGuestUser("guest"+ now.toString());
        }

            order.setUser(user);
        order.setPosition(position);
        order.setStatus(false);
        order.setDish(dish);
        order.setQuantity(orderRequest.getQuantity());
        Long timeServing = dish.getCookingTime()*orderRequest.getQuantity();
        Double totalPrice = dish.getPrice()*orderRequest.getQuantity();
        order.setTimeServing(timeServing);
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setUser(modelMapper.map(user, UserResponse.class));
        orderResponse.setDish(modelMapper.map(dish, DishResponse.class));
        orderResponse.setPosition(modelMapper.map(position, PositionResponse.class));
        return orderResponse;
    }


    @Override
    public String updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(true);
        orderRepository.save(order);

        return "Updated order status successfully";
    }

    @Override
    public String updateOrderUser(Long id, Long userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        order.setUser(user);
        orderRepository.save(order);
        return "Updated order user successfully";
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        return orderResponse;
    }

    @Override
    public PageCustom<OrderResponse> getAllOrders(Pageable pageable) {
       Page<Order> page=orderRepository.findAll(pageable);
       PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
               .pageNo(page.getNumber()+1)
               .pageSize(page.getSize())
               .totalPages(page.getTotalPages())
               .pageContent(page.getContent().stream().map(order->modelMapper.map(order, OrderResponse.class)).toList())
               .build();

        return pageCustom;

    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
        return "Deleted order successfully";
    }

    @Override
    public PageCustom<OrderResponse> getOrdersByPosition(Long positionId, Pageable pageable) {
        Position position = positonRepository.findById(positionId).orElseThrow(() -> new ResourceNotFoundException("Position", "id", positionId));
        Page<Order> orders = orderRepository.findAllByPositionAndStatus(position,false,pageable);
        PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                .pageNo(orders.getNumber()+1)
                .pageSize(orders.getSize())
                .totalPages(orders.getTotalPages())
                .pageContent(orders.getContent().stream().map(order->modelMapper.map(order, OrderResponse.class)).toList())
                .build();
        return pageCustom;
    }


    @Override
    public PageCustom<OrderResponse> getOrdersByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Position", "id", userId));
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                .pageNo(orders.getNumber() + 1)
                .pageSize(orders.getSize())
                .totalPages(orders.getTotalPages())
                .pageContent(orders.getContent().stream().map(order -> modelMapper.map(order, OrderResponse.class)).toList())
                .build();
        return pageCustom;
    }



}
