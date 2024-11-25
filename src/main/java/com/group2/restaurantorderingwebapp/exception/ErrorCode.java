package com.group2.restaurantorderingwebapp.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(404, "User does not exist", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(401, "User is not authenticated", HttpStatus.UNAUTHORIZED),
    USER_LOGGED_OUT(401, "User is logged out", HttpStatus.UNAUTHORIZED),
    USER_TOKEN_INCORRECT(403, "User token is incorrect", HttpStatus.FORBIDDEN),

    ORDER_NOT_EXISTED(404, "Order does not exist", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_EXISTED(404, "Category does not exist", HttpStatus.NOT_FOUND),

    DISH_NOT_EXISTED(404, "Dish does not exist", HttpStatus.NOT_FOUND),

    COMMENT_NOT_EXISTED(404, "Comment does not exist", HttpStatus.NOT_FOUND),

    EMAIL_EXISTED(400, "Email already exists", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(404, "Email not found", HttpStatus.NOT_FOUND),

   PHONE_EXISTED(400, "Phone already exists", HttpStatus.BAD_REQUEST),
    DISH_EXISTED(400, "Dish already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(400, "Category already exists", HttpStatus.BAD_REQUEST),
    EMAIL_CAN_NOT_UPDATE(400, "Email can not update", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_CAN_NOT_UPDATE(400, "Phone number can not update", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(400, "Username already exists", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_EMAIL_OR_PHONE_CAN_NOT_CHANGE(400, "User email or phone can not change", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus status;
}
