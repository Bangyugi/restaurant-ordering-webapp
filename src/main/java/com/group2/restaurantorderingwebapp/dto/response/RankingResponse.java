package com.group2.restaurantorderingwebapp.dto.response;

import com.group2.restaurantorderingwebapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponse {
    private Long id;
    private String comment;
    private int star;
    private UserResponse User;
}
