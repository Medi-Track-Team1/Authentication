package com.example.authorization.controller;


import com.example.authorization.model.error;
import com.example.authorization.utils.customException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<error>handlingAllException(Exception ex){
        error e=new error(LocalDateTime.now(),ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new  ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(customException.class)
    public ResponseEntity<error>customException(customException exp){
        error e=new error(LocalDateTime.now(),exp.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }
}
