package com.group2.restaurantorderingwebapp.dto.response;

import com.group2.restaurantorderingwebapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String address;
    private String phoneNumber;
    private String email;
    private String gender;
    private LocalDate Dob;
    private boolean status;
    private CartResponse cart;
    private Set<Role> roles;
}
