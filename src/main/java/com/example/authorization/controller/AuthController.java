package com.example.authorization.controller;

import com.example.authorization.dto.UserRegisterDTO;
import com.example.authorization.dto.userLoginDTO;
import com.example.authorization.model.User;
import com.example.authorization.service.Authservice;
import com.example.authorization.service.JwtService;
import com.example.authorization.utils.customException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Authservice authService;








    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userDTO) {
        try {
            User savedUser = authService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody userLoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String jwt = jwtService.generateToken(loginDTO.getEmail());
                User user = authService.getUserByEmail(loginDTO.getEmail())
                        .orElseThrow(() -> new customException("User not found"));
                // Returning token as JSON response
                Map<String, String> response = new HashMap<>();
                response.put("token", jwt);
                response.put("message", "Login successful");
                response.put("userid", authService.getUserid(loginDTO.getEmail()));
                response.put("username", user.getUsername());
//                System.out.println(loginDTO.getUserid());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            User user = authService.getUserByEmail(email)
                    .orElseThrow(() -> new customException("User with email " + email + " not found"));

            // In a real application, you would send a password reset email here
            // For this example, we'll just return a success message
            return ResponseEntity.ok(Map.of(
                    "message", "Password reset link sent to email",
                    "userId", user.getUserid()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam Long userId,
            @RequestParam String newPassword) {
        try {
            User updatedUser = authService.resetPassword(userId, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}
@RestController
@RequestMapping("/api/test")
 class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Admin content.");
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> doctorAccess() {
        return ResponseEntity.ok("Doctor content.");
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> patientAccess() {
        return ResponseEntity.ok("Patient content.");
    }
}

