package com.group2.restaurantorderingwebapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VnPayResponse {
    int code;
    String message;
    public String paymentUrl;
}
