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
import com.group2.restaurantorderingwebapp.service.RedisService;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final PositonRepository positonRepository;

    private final RedisService redisService;
    private final UserService userService;

    private final static String KEY = "order";


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user;
        if (orderRequest.getUserId() != null) {
            user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", orderRequest.getUserId()));
        } else {
            LocalDateTime now = LocalDateTime.now();
            user = userService.createGuestUser("guest" + now);
        }

        if (orderRepository.existsByDishAndPositionAndStatus(orderRequest.getDishId(), orderRequest.getPositionId(), false)) {
            Order existingOrder = orderRepository.findByDishAndPositionAndStatus(orderRequest.getDishId(), orderRequest.getPositionId(), false).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
            existingOrder.setQuantity(existingOrder.getQuantity() + orderRequest.getQuantity());
            existingOrder.setTotalPrice(existingOrder.getTotalPrice() + orderRequest.getQuantity() * existingOrder.getDish().getPrice());
            existingOrder.setTimeServing(existingOrder.getTimeServing() + orderRequest.getQuantity() * existingOrder.getDish().getCookingTime());
            orderRepository.save(existingOrder);
            OrderResponse orderResponse = modelMapper.map(existingOrder, OrderResponse.class);
            orderResponse.setUser(modelMapper.map(user, UserResponse.class));
            redisService.deleteAll(KEY);
            return orderResponse;
        }

        Position position = positonRepository.findById(orderRequest.getPositionId()).orElseThrow(() -> new ResourceNotFoundException("Position", "id", orderRequest.getPositionId()));
        Dish dish = dishRepository.findById(orderRequest.getDishId()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", orderRequest.getDishId()));

        Order order = new Order();
        order.setUser(user);
        order.setPosition(position);
        order.setStatus(false);
        order.setDish(dish);
        order.setQuantity(orderRequest.getQuantity());
        Long timeServing = dish.getCookingTime() * orderRequest.getQuantity();
        Double totalPrice = dish.getPrice() * orderRequest.getQuantity();
        order.setTimeServing(timeServing);
        order.setTotalPrice(totalPrice);

        order = orderRepository.save(order);

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        redisService.deleteAll(KEY);
        return orderResponse;
    }


    @Override
    public String updatePaymentStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(true);
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Updated pay status successfully";
    }

    @Override
    public String updateOrderStatus(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setOrderStatus(true);
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Update order status successfully";
    }

    @Override
    public String updateRatingStatus(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setRatingStatus(true);
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Update rating status successfully";
    }



    @Override
    public String updateOrderUser(Long id, Long userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        order.setUser(user);
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Updated order user successfully";
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        String field = "orderById:" + id;
        var json = redisService.getHash(KEY, field);
        if (json == null) {

            Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            redisService.setHashRedis(KEY, field, redisService.convertToJson(orderResponse));
            return orderResponse;
        }
        return redisService.convertToObject((String) json, OrderResponse.class);
    }

    @Override
    public PageCustom<OrderResponse> getAllOrders(Pageable pageable) {
        String field = "allOrders" + pageable.toString();
        var json = redisService.getHash(KEY, field);
        if (json == null) {

            Page<Order> page = orderRepository.findAll(pageable);
            PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                    .pageNo(page.getNumber() + 1)
                    .pageSize(page.getSize())
                    .totalPages(page.getTotalPages())
                    .pageContent(page.getContent().stream().map(order -> modelMapper.map(order, OrderResponse.class)).toList())
                    .build();
            redisService.setHashRedis(KEY, field, redisService.convertToJson(pageCustom));
            return pageCustom;
        }
        return redisService.convertToObject((String) json, PageCustom.class);

    }



    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
        redisService.deleteAll(KEY);
        return "Deleted order successfully";
    }

    @Override
    public PageCustom<OrderResponse> getOrdersByPosition(Long positionId, Pageable pageable) {
        String field = "getOrdersByPosition:" + positionId + pageable.toString();
        var json = redisService.getHash(KEY, field);
        if (json == null) {

            Position position = positonRepository.findById(positionId).orElseThrow(() -> new ResourceNotFoundException("Position", "id", positionId));
            Page<Order> orders = orderRepository.findAllByPositionAndStatus(position, false, pageable);
            PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                    .pageNo(orders.getNumber() + 1)
                    .pageSize(orders.getSize())
                    .totalPages(orders.getTotalPages())
                    .pageContent(orders.getContent().stream().map(order -> modelMapper.map(order, OrderResponse.class)).toList())
                    .build();
            redisService.setHashRedis(KEY, field, redisService.convertToJson(pageCustom));
            return pageCustom;
        }
        return redisService.convertToObject((String) json, PageCustom.class);
    }


    @Override
    public PageCustom<OrderResponse> getOrdersByUser(Long userId, Pageable pageable) {
        String field = "getOrdersByUser:" + userId + pageable.toString();
        var json = redisService.getHash(KEY, field);
        if (json == null) {

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Position", "id", userId));
            Page<Order> orders = orderRepository.findAllByUserAndStatus(user,true, pageable);
            PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                    .pageNo(orders.getNumber() + 1)
                    .pageSize(orders.getSize())
                    .totalPages(orders.getTotalPages())
                    .pageContent(orders.getContent().stream().map(order -> modelMapper.map(order, OrderResponse.class)).toList())
                    .build();
            redisService.setHashRedis(KEY, field, redisService.convertToJson(pageCustom));
            return pageCustom;
        }
        return redisService.convertToObject((String) json, PageCustom.class);
    }


}
