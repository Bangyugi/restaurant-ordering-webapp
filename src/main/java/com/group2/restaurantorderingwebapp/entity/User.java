package com.group2.restaurantorderingwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
@Builder
public class User extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(columnDefinition = "TEXT" )
    private String imageUrl;
    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" ,nullable = false)
    private String firstName;
    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" ,nullable = false)
    private String lastName;
    @Column(columnDefinition = "varchar(50) collate 'utf8_bin'" ,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" )
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private LocalDate Dob;
    @Builder.Default
    @Column(columnDefinition = "TINYINT")
    private boolean status = true;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Order> orders;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Ranking> rankings;


}
