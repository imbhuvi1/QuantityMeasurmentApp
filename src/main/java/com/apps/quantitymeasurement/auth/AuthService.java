package com.apps.quantitymeasurement.auth;

import com.apps.quantitymeasurement.dto.AuthResponse;
import com.apps.quantitymeasurement.dto.LoginRequest;
import com.apps.quantitymeasurement.dto.RegisterRequest;
import com.apps.quantitymeasurement.user.UserEntity;
import com.apps.quantitymeasurement.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already registered");

        UserEntity user = new UserEntity(
                request.getEmail(),
                request.getName(),
                passwordEncoder.encode(request.getPassword()),
                UserEntity.AuthProvider.LOCAL
        );
        
        // Set role from request
        user.setRole(request.getRoleEnum());
        
        userService.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole().toString());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserEntity user = userService.findByEmail(request.getEmail());
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole().toString());
    }
}
