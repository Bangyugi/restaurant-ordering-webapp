package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.User;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User createUser(UserResponse userResponse) {
        User user = modelMapper.map(userResponse, User.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userResponse.getPassword()));
        user.setStatus(true);
        // set role -> weekend:v
        userRepository.save(user);
        return user;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable).map(x -> modelMapper.map(x, UserResponse.class));
    }

    @Override
    public User updateUser(Long id, UserResponse userResponse) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        modelMapper.map(userResponse, user);
        userRepository.save(user);
        return user;
    }

    @Override
    public String deleteUser(Long id) {
        userRepository
                .findById(id)
                .ifPresent(userRepository::delete);
        return "User with id: " +id+ " was deleted successfully";
    }
}


