package com.example.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor

public class error {
    private LocalDateTime timestap;
    private  String message;
    private  int  status;

}
