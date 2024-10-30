package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.RoleRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createRole (@Valid @RequestBody RoleRequest roleRequest){
        ApiResponse apiResponse = ApiResponse.success(roleService.createRole(roleRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> getRoleByName(@PathVariable("name") String name){
        ApiResponse apiResponse = ApiResponse.success(roleService.getRoleByRoleName(name));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllRole(){
        ApiResponse apiResponse = ApiResponse.success(roleService.getAllRole());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse> deleteRoleByName(@PathVariable("name") String name){
        ApiResponse apiResponse = ApiResponse.success(roleService.deleteRoleByRoleName(name));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
