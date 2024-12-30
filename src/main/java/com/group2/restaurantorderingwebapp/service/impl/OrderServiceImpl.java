package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.response.*;
import com.group2.restaurantorderingwebapp.entity.*;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.repository.*;
import com.group2.restaurantorderingwebapp.service.OrderService;
import com.group2.restaurantorderingwebapp.service.RedisService;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;
    private final CartRepository cartRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user;
        if (orderRequest.getUserId() != null) {
            user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", orderRequest.getUserId()));
        } else {
            LocalDateTime now = LocalDateTime.now();
            user = userService.createGuestUser("guest" + now);
        }
        Dish dish = dishRepository.findById(orderRequest.getDishId()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", orderRequest.getDishId()));

        if( orderRequest.getCartId() == null && orderRequest.getPositionId() != null){

            if (orderRepository.existsByDishAndPositionAndOrderStatus(orderRequest.getDishId(), orderRequest.getPositionId(), "Considering")) {
                Order existingOrder = orderRepository.findByDishAndPositionAndStatus(orderRequest.getDishId(), orderRequest.getPositionId(), false).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
                if (dish.getServedAmount()< orderRequest.getQuantity() + existingOrder.getQuantity()){
                    throw  new AppException(ErrorCode.ORDER_REACHED_MAX_QUANTITY);
                }
                existingOrder.setQuantity(existingOrder.getQuantity() + orderRequest.getQuantity());
                existingOrder.setTotalPrice(existingOrder.getTotalPrice() + orderRequest.getQuantity() * existingOrder.getDish().getPrice());
                existingOrder.setTimeServing(existingOrder.getTimeServing() + orderRequest.getQuantity() * existingOrder.getDish().getCookingTime());
                orderRepository.save(existingOrder);
                OrderResponse orderResponse = modelMapper.map(existingOrder, OrderResponse.class);
                orderResponse.setUser(modelMapper.map(user, UserResponse.class));
                messagingTemplate.convertAndSend("/topic/orders", orderResponse);
                redisService.deleteAll(KEY);
                return orderResponse;
            }
        }else if(orderRequest.getCartId() != null && orderRequest.getPositionId() == null){

            if (orderRepository.existsByDishAndPositionAndOrderStatus(orderRequest.getDishId(), orderRequest.getPositionId(), "Considering")) {
                Order existingOrder = orderRepository.findByDishAndCartAndStatus(orderRequest.getDishId(), orderRequest.getCartId(), false).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
                if (dish.getServedAmount()< orderRequest.getQuantity() + existingOrder.getQuantity()){
                    throw  new AppException(ErrorCode.ORDER_REACHED_MAX_QUANTITY);
                }
                existingOrder.setQuantity(existingOrder.getQuantity() + orderRequest.getQuantity());
                existingOrder.setTotalPrice(existingOrder.getTotalPrice() + orderRequest.getQuantity() * existingOrder.getDish().getPrice());
                existingOrder.setTimeServing(existingOrder.getTimeServing() + orderRequest.getQuantity() * existingOrder.getDish().getCookingTime());
                orderRepository.save(existingOrder);
                OrderResponse orderResponse = modelMapper.map(existingOrder, OrderResponse.class);
                orderResponse.setUser(modelMapper.map(user, UserResponse.class));
                messagingTemplate.convertAndSend("/topic/orders", orderResponse);
                redisService.deleteAll(KEY);
                return orderResponse;
            }

        }
        else throw new AppException(ErrorCode.POSITION_OR_CART_NOT_FOUND);


        if (dish.getServedAmount()< orderRequest.getQuantity()){
            throw  new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
        }

        Order order = new Order();
        order.setUser(user);
        if(orderRequest.getCartId() ==null && orderRequest.getPositionId() != null){
            Position position = positonRepository.findById(orderRequest.getPositionId()).orElseThrow(() -> new ResourceNotFoundException("Position", "id", orderRequest.getPositionId()));
            order.setPosition(position);
        }
        else if(orderRequest.getCartId() != null && orderRequest.getPositionId() == null){
            Cart cart = cartRepository.findById(orderRequest.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", orderRequest.getCartId()));
            order.setCart(cart);
        }
        else throw new AppException(ErrorCode.POSITION_OR_CART_NOT_FOUND);
        order.setDish(dish);
        order.setQuantity(orderRequest.getQuantity());
        Long timeServing = dish.getCookingTime() * orderRequest.getQuantity();
        Double totalPrice = dish.getPrice() * orderRequest.getQuantity();
        order.setTimeServing(timeServing);
        order.setTotalPrice(totalPrice);

        order = orderRepository.save(order);

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        messagingTemplate.convertAndSend("/topic/orders", orderResponse);
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
    public String confirmOrderStatus(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        Dish dish = dishRepository.findById(order.getDish().getDishId()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", order.getDish().getDishId()));
        if (order.getQuantity() > dish.getServedAmount()){
            throw  new AppException(ErrorCode.SOMEONE_FASTER);
        }
        dish.setServedAmount(dish.getServedAmount() - order.getQuantity());
        if (dish.getServedAmount() == 0){
            dish.setStatus("Out of stock");
        }
        redisService.deleteAll("dish");
        dishRepository.save(dish);
        order.setOrderStatus("Serving");
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Update order status successfully";
    }

    @Override
    public String updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setOrderStatus(status);
        orderRepository.save(order);
        redisService.deleteAll(KEY);
        return "Update order status to " + status + " successfully";
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
            messagingTemplate.convertAndSend("/topic/orders", pageCustom);
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

    @Override
    public PageCustom<OrderResponse> getOrdersByCart(Long cartId, Pageable pageable) {
        String field = "getOrdersByCart:" + cartId + pageable.toString();
        var json = redisService.getHash(KEY, field);
        if (json == null) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
            Page<Order> orders = orderRepository.findAllByCartAndStatus(cart, false, pageable);
            PageCustom<OrderResponse> pageCustom = PageCustom.<OrderResponse>builder()
                    .pageNo(orders.getNumber() + 1)
                    .pageSize(orders.getSize())
                    .totalPages(orders.getTotalPages())
                    .pageContent(orders.getContent().stream().map(order -> modelMapper.map(order, OrderResponse.class)).toList())
                    .build();
            redisService.setHashRedis(KEY, field, redisService.convertToJson(pageCustom));
            messagingTemplate.convertAndSend("/topic/orders", pageCustom);
            return pageCustom;
        }
        return redisService.convertToObject((String) json, PageCustom.class);
    }
}
