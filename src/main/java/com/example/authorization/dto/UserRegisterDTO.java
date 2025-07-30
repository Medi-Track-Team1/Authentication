package com.example.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Set<String> roles; // Role names like "ROLE_PATIENT"
    private String userid;
}
