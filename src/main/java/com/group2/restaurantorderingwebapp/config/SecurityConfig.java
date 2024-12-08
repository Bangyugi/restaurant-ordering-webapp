package com.group2.restaurantorderingwebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> corsFilter())
                .authorizeHttpRequests(
                        config->config
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("swagger-ui/index.html" +"/**"
                                        , "/v3/api-docs/**"
                                        , "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/dishes/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/rankings/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/positions/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/orders").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/orders/{orderId}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/orders/{orderId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/orders/{orderId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/orders/position/{positionId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/orders/user/{userId}").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/api/order/{orderId}/update-user").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/rankings").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/api/users/{userId}").permitAll()
                                .anyRequest().hasAnyRole("ADMIN","MANAGER")
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
