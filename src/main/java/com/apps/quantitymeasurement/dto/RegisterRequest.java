package com.apps.quantitymeasurement.dto;

import com.apps.quantitymeasurement.user.UserEntity;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private String role; // USER or ADMIN
    
    // Helper method to get role enum
    public UserEntity.Role getRoleEnum() {
        if (role != null && role.equalsIgnoreCase("ADMIN")) {
            return UserEntity.Role.ADMIN;
        }
        return UserEntity.Role.USER; // Default to USER
    }
}
