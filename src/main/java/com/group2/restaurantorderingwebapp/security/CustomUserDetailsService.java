package com.group2.restaurantorderingwebapp.security;

import com.group2.restaurantorderingwebapp.entity.User;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhoneNumber(username,username).orElseThrow(()->new UsernameNotFoundException("User not found with username"));
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}

