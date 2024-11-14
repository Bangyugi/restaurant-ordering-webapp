package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.UserRequest;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    UserResponse updateUser(Long id, UserRequest userRequest);

    String deleteUser(Long id);
}
