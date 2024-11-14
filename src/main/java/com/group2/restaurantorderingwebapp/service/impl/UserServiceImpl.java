package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.UserRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        if (userRepository.existsByEmail(userRequest.getEmail()))
        {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setStatus(true);
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRole("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("role","role's name","ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);
        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByEmailOrPhoneNumber(username,username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return userRepository.findAll(pageable).stream().map(result -> modelMapper.map(result,UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        modelMapper.map(userRequest, user);
        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return "User with id: " +id+ " was deleted successfully";
    }
}


