package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.UserRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService useService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        ApiResponse apiResponse = ApiResponse.success(useService.createUser(userRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUser(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "lastName",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
    ){
        ApiResponse apiResponse = ApiResponse.success(useService.getAllUser(pageNo,pageSize,sortBy,sortDir));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
      @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") Long id){
        ApiResponse apiResponse = ApiResponse.success(useService.getUserById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @GetMapping("/username/{username}")
//    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable("username") String username){
//        ApiResponse apiResponse = ApiResponse.success(useService.getUserByUsername(username));
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest){
        ApiResponse apiResponse = ApiResponse.success(useService.updateUser(id, userRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.success(useService.deleteUser(id));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
