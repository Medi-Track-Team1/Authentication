package com.example.authorization;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class AuthorizationApplication {

    public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.load();
//
//        // Set environment variables as System properties so Spring Boot can read them
//        System.setProperty("DB_URL", dotenv.get("DB_URL"));
//        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));


        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
