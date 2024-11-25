package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.UserRequest;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.Role;
import com.group2.restaurantorderingwebapp.entity.User;
import com.group2.restaurantorderingwebapp.exception.AppException;
import com.group2.restaurantorderingwebapp.exception.ErrorCode;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.RoleRepository;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createGuestUser(String username) {

       User user = new User();
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_GUEST").orElseThrow(()->new ResourceNotFoundException("role","role's name","ROLE_GUEST"));
        roles.add(role);
        user.setRoles(roles);
        user.setFirstName("Guest");
        user.setLastName(LocalDateTime.now().toString());
        userRepository.save(user);

        return user;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserResponse.class);
    }


    @Override
    public PageCustom<UserResponse> getAllUser(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        PageCustom<UserResponse> pageCustom = PageCustom.<UserResponse>builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageContent(page.getContent().stream().map(user->modelMapper.map(user, UserResponse.class)).toList())
                .build();
        return pageCustom;
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!userRequest.getEmailOrPhone().equals(user.getEmailOrPhone())){
            throw new AppException(ErrorCode.USER_EMAIL_OR_PHONE_CAN_NOT_CHANGE);
        }
        modelMapper.map(userRequest,user);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("role", "role's name","ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);


  

        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return "User with id: " +userId+ " was deleted successfully";
    }
}


