package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.OrderRequest;
import com.group2.restaurantorderingwebapp.dto.request.ReservationRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.dto.response.ReservationResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.*;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.*;
import com.group2.restaurantorderingwebapp.service.OrderService;
import com.group2.restaurantorderingwebapp.service.RedisService;
import com.group2.restaurantorderingwebapp.service.ReservationService;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PositonRepository positonRepository;

    private final RedisService redisService;
    private final UserService userService;

    private final static String KEY = "reservation";
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        User user;
        if (reservationRequest.getUserId() != null) {
            user = userRepository.findById(reservationRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", reservationRequest.getUserId()));
        } else {
            LocalDateTime now = LocalDateTime.now();
            user = userService.createGuestUser("guest" + now);
        }
        List<Order> orders = new ArrayList<>();
        long totalAmount = 0;
        for(Long orderId : reservationRequest.getOrders()) {
            Order order = orderRepository.findByOrderIdAndStatus(orderId, false).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
            orders.add(order);
            totalAmount += order.getTotalPrice();
        }

        List<Position> position = positonRepository.findByPositionIdIn(reservationRequest.getPositionId());
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        reservation.setUser(user);
        reservation.setOrders(orders);
        reservation.setPosition(position);
        reservation.setStatus(1);
        reservation = reservationRepository.save(reservation);
        redisService.deleteAll(KEY);
        ReservationResponse reservationResponse = modelMapper.map(reservation, ReservationResponse.class);
        return reservationResponse;
    }

    @Override
    public String deleteOrder(Long id) {
        return "";
    }


}
