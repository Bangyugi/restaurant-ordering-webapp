package com.group2.restaurantorderingwebapp.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    @Builder.Default
    private String status="Pending";

    private LocalDateTime orderTime;

    private boolean type;

    private String address;

    private String fullname;

    private String phone;

    private String note;

    private Long userId;

    private Set<Long> positionIds;

    private Set<Long> orderIds;
}
