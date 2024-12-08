package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.LoginRequest;
import com.group2.restaurantorderingwebapp.dto.request.RegisterRequest;
import com.group2.restaurantorderingwebapp.dto.response.JwtAuthResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;

public interface AuthenticationService {
    JwtAuthResponse login (LoginRequest loginRequest);

    JwtAuthResponse refreshToken(String refreshToken);

    String register(RegisterRequest registerRequest);
}
