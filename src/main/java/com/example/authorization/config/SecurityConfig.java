package com.example.authorization.config;


import com.example.authorization.utils.CustomAccessDeniedHandler;
import com.example.authorization.utils.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable())  // Disabled for JWT
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**").permitAll()  // Public endpoints
                        .requestMatchers("/api/test/admin/**").hasRole("ADMIN")  // Role checks
                        .requestMatchers("/api/test/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/api/test/patient/**").hasRole("PATIENT")
                        .anyRequest().authenticated())  // All others require auth
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)  // Handles 401
                        .accessDeniedHandler(accessDeniedHandler)  // Handles 403
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No sessions
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // JWT filter
                .authenticationProvider(authProvider());  // Your auth setup


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }




//      @Bean public UserDetailsService userDetailsService() {
//
//      UserDetails user=User .withDefaultPasswordEncoder() .username("navin")
//      .password("n@123") .roles("USER") .build();
//
//     UserDetails admin=User .withDefaultPasswordEncoder() .username("admin")
//        .password("admin@789") .roles("ADMIN") .build();
//
//      return new InMemoryUserDetailsManager(user,admin); }



}
