package com.apps.quantitymeasurement.admin;

import com.apps.quantitymeasurement.dto.ApiResponse;
import com.apps.quantitymeasurement.user.UserEntity;
import com.apps.quantitymeasurement.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Get all users (Admin only)
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAllUsers(Authentication auth) {
        try {
            // Check if current user is admin
            String email = auth.getName();
            Optional<UserEntity> currentUser = userRepository.findByEmail(email);
            
            if (currentUser.isEmpty() || !currentUser.get().isAdmin()) {
                return ResponseEntity.status(403)
                    .body(new ApiResponse<>(false, "Access denied. Admin role required.", null));
            }

            // Get all users
            List<UserEntity> users = userRepository.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error retrieving users: " + e.getMessage(), null));
        }
    }

    // Get user by ID (Admin only)
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> getUserById(@PathVariable Long id, Authentication auth) {
        try {
            // Check if current user is admin
            String email = auth.getName();
            Optional<UserEntity> currentUser = userRepository.findByEmail(email);
            
            if (currentUser.isEmpty() || !currentUser.get().isAdmin()) {
                return ResponseEntity.status(403)
                    .body(new ApiResponse<>(false, "Access denied. Admin role required.", null));
            }

            // Find user by ID
            Optional<UserEntity> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "User not found", null));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, "User found", user.get()));
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error retrieving user: " + e.getMessage(), null));
        }
    }

    // Delete user (Admin only)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id, Authentication auth) {
        try {
            // Check if current user is admin
            String email = auth.getName();
            Optional<UserEntity> currentUser = userRepository.findByEmail(email);
            
            if (currentUser.isEmpty() || !currentUser.get().isAdmin()) {
                return ResponseEntity.status(403)
                    .body(new ApiResponse<>(false, "Access denied. Admin role required.", null));
            }

            // Check if user exists
            Optional<UserEntity> userToDelete = userRepository.findById(id);
            if (userToDelete.isEmpty()) {
                return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "User not found", null));
            }

            // Don't allow admin to delete themselves
            if (userToDelete.get().getEmail().equals(email)) {
                return ResponseEntity.status(400)
                    .body(new ApiResponse<>(false, "Cannot delete your own account", null));
            }

            // Delete user
            userRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", "User ID: " + id));
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error deleting user: " + e.getMessage(), null));
        }
    }

    // Get admin dashboard stats
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStats>> getStats(Authentication auth) {
        try {
            // Check if current user is admin
            String email = auth.getName();
            Optional<UserEntity> currentUser = userRepository.findByEmail(email);
            
            if (currentUser.isEmpty() || !currentUser.get().isAdmin()) {
                return ResponseEntity.status(403)
                    .body(new ApiResponse<>(false, "Access denied. Admin role required.", null));
            }

            // Calculate stats
            long totalUsers = userRepository.count();
            long adminUsers = userRepository.countByRole(UserEntity.Role.ADMIN);
            long regularUsers = totalUsers - adminUsers;

            AdminStats stats = new AdminStats(totalUsers, regularUsers, adminUsers);
            return ResponseEntity.ok(new ApiResponse<>(true, "Stats retrieved successfully", stats));
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error retrieving stats: " + e.getMessage(), null));
        }
    }

    // Simple stats class
    public static class AdminStats {
        public long totalUsers;
        public long regularUsers;
        public long adminUsers;

        public AdminStats(long totalUsers, long regularUsers, long adminUsers) {
            this.totalUsers = totalUsers;
            this.regularUsers = regularUsers;
            this.adminUsers = adminUsers;
        }
    }
}