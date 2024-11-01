package com.group2.restaurantorderingwebapp.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" ,nullable = false)
    private String firstName;
    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" ,nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(120) collate 'utf8_bin'" ,unique = true ,nullable = false)
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private LocalDate Dob;
    private int amount;
    @Column(columnDefinition = "TINYINT")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Order> orders;
}
