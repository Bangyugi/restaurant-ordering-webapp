package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.RoleRequest;
import com.group2.restaurantorderingwebapp.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole (RoleRequest roleRequest);

    RoleResponse getRoleByRoleName(String roleName);

    List<RoleResponse> getAllRole();

    String deleteRoleByRoleName(String roleName);
}
