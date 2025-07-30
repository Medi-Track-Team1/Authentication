package com.example.authorization.utils;



public class customException extends RuntimeException{
    String message;
    public customException(String message) {
        super(message);
    }
}

