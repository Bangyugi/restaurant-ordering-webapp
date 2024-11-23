package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.LoginRequest;
import com.group2.restaurantorderingwebapp.dto.request.RegisterRequest;
import com.group2.restaurantorderingwebapp.dto.response.JwtAuthResponse;

public interface AuthenticationService {
    JwtAuthResponse login (LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
}
