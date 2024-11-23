package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.UserRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService useService;



    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUser(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "lastName",required = false) String sortBy

    ){
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy).ascending() );
        ApiResponse apiResponse = ApiResponse.success(useService.getAllUser(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
      @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") Long userId){
        ApiResponse apiResponse = ApiResponse.success(useService.getUserById(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable("username") String username){
        ApiResponse apiResponse = ApiResponse.success(useService.getUserByUsername(username));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserRequest userRequest){
        ApiResponse apiResponse = ApiResponse.success(useService.updateUser(userId, userRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId){
        ApiResponse apiResponse = ApiResponse.success(useService.deleteUser(userId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
