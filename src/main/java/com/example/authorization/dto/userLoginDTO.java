package com.example.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userLoginDTO {
           private String email;
           private String password;
           private String username;
           private String userid;
           private String role;
}

