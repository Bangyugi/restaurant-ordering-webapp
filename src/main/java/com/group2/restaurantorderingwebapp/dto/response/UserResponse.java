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
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private LocalDate Dob;
    private boolean status;
    private Set<Role> roles;
}
