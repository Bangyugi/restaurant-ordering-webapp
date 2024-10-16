package com.group2.restaurantorderingwebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:5173","https://restaurant-ba062.web.app","http://localhost:5174","http://localhost:5175")
                .allowedMethods("*")
                .allowedHeaders("*");

    }
}
