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

    EMAIL_NOT_FOUND(404, "Email not found", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
