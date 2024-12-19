package com.group2.restaurantorderingwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class RestaurantOrderingWebappApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantOrderingWebappApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RestaurantOrderingWebappApplication.class, args);
        LOGGER.trace("doStuff needed more information - {}", "bang tran van dep trai");
        LOGGER.debug("doStuff needed to debug - {}", "the boy savior");
        LOGGER.info("doStuff took input - {}", "it's just a test");
        LOGGER.warn("doStuff needed to warn - {}", "it's so beautiful");
        LOGGER.error("doStuff encountered an error with value - {}", "hello kitty");
    }

}
