package com.group2.restaurantorderingwebapp.dto.request;

import com.group2.restaurantorderingwebapp.entity.Order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long userId;
    @NotNull(message = "OrderItemId cannot be null")
    private List<Long> orders;
    private List<Long> positionId;
    @NotNull(message = "typeReservation cannot be null")
    private int typeReservation;
    private LocalDateTime orderTime;
}
