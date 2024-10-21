package com.group2.restaurantorderingwebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String address;
    private String gender;
    private LocalDate Dob;
    private boolean status;
}
