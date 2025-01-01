package com.group2.restaurantorderingwebapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CartResponse {
    private Long cartId;
    private int elementAmount;
}
