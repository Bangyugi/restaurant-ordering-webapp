package com.group2.restaurantorderingwebapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private Long paymentId;
    private String paymentMethod;
    private long amount;
    private UserResponse user;
    private List<OrderResponse> orders;
}
