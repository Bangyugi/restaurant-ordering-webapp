package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserResponse userResponse);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUser(Pageable pageable);

    User updateUser(Long id, UserResponse userResponse);

    String deleteUser(Long id);

}
