package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.LoginRequest;
import com.group2.restaurantorderingwebapp.dto.request.RegisterRequest;

public interface AuthenticationService {
    String login (LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
}
