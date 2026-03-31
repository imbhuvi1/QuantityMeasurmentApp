package com.apps.quantitymeasurement.user;

import com.apps.quantitymeasurement.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<Map<String, String>>> getProfile(Principal principal) {
        UserEntity user = userService.findByEmail(principal.getName());
        Map<String, String> data = Map.of(
            "name", user.getName() != null ? user.getName() : "",
            "email", user.getEmail(),
            "phone", user.getPhone() != null ? user.getPhone() : "",
            "bio", user.getBio() != null ? user.getBio() : "",
            "provider", user.getProvider().name()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, data, "Profile fetched"));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<String>> updateProfile(Principal principal, @RequestBody Map<String, String> body) {
        UserEntity user = userService.findByEmail(principal.getName());
        if (body.containsKey("name")) user.setName(body.get("name"));
        if (body.containsKey("phone")) user.setPhone(body.get("phone"));
        if (body.containsKey("bio")) user.setBio(body.get("bio"));
        userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Profile updated"));
    }
}
