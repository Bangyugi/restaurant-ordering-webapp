package com.group2.restaurantorderingwebapp.exception;

import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest)
    {
        ApiResponse apiResponse = ApiResponse.error(404, exception.getMessage());
        webRequest.getDescription(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException exception,WebRequest webRequest){
        ApiResponse apiResponse = ApiResponse.error(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage());
        webRequest.getDescription(false);
        return new ResponseEntity<>(apiResponse,exception.getErrorCode().getStatus());

    }

    // global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception exception,WebRequest webRequest){
        ApiResponse apiResponse = ApiResponse.error(500, exception.getMessage());
        webRequest.getDescription(false);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        ApiResponse apiResponse = ApiResponse.error(403, "Access is denied: " + exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

}



