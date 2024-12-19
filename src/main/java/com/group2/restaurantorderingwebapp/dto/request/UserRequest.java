package com.group2.restaurantorderingwebapp.dto.request;

import com.group2.restaurantorderingwebapp.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    @Builder.Default
    private String imageUrl = "https://images.squarespace-cdn.com/content/v1/54b7b93ce4b0a3e130d5d232/1519987020970-8IQ7F6Z61LLBCX85A65S/icon.png?format=1000w";
    private String emailOrPhone;
    private String address;
    private String gender;
    private LocalDate Dob;
}
