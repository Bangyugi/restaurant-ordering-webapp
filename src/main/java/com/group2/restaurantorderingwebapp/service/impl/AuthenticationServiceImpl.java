package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.LoginRequest;
import com.group2.restaurantorderingwebapp.dto.request.RegisterRequest;
import com.group2.restaurantorderingwebapp.dto.response.JwtAuthResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.Role;
import com.group2.restaurantorderingwebapp.entity.User;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.RoleRepository;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.AuthenticationService;
import com.group2.restaurantorderingwebapp.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailOrPhone(loginRequest.getEmailOrPhone()).orElseThrow(() -> new ResourceNotFoundException("User", "email or phone number", loginRequest.getEmailOrPhone()));
        if (user == null) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }
        try {

          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmailOrPhone(), loginRequest.getPassword()
            ));
        } catch (Exception exception) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }

        String jwtToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24*3);
        String refreshToken = jwtService.generateToken(user,jwtService.getExpirationTime()*24*7);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }

    @Override
    public JwtAuthResponse refreshToken(String refreshToken) {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
         String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmailOrPhone(username).orElseThrow(() -> new ResourceNotFoundException("User", "email or phone number", username));
        String accessToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24) ;
        refreshToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24*2);

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }



    @Override
    public String register(RegisterRequest registerRequest){
        if (userRepository.existsByEmailOrPhone(registerRequest.getEmailOrPhone())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("role", "role's name","ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!.";
    }


}
