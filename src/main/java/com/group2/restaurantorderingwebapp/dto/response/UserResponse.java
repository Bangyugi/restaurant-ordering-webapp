package com.group2.restaurantorderingwebapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String address;
    private String emailOrPhone;
    private String gender;
    private LocalDate Dob;
    private boolean status;
    private Set<Role> roles;
}
