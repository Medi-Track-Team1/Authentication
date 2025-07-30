package com.example.authorization.service;
import com.example.authorization.dto.UserRegisterDTO;
import com.example.authorization.model.ERole;
import com.example.authorization.model.Role;
import com.example.authorization.model.User;
import com.example.authorization.reposistory.RoleRepository;
import com.example.authorization.reposistory.userreposistory;
import com.example.authorization.utils.customException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

@Service
public class Authservice {

    @Autowired
    private userreposistory userRepo;
    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Add new user with encoded password and roles
    public User addUser(UserRegisterDTO dto) {
        System.out.println("DTO received: " + dto.toString());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setEnabled(dto.isEnabled());
        user.setUserid(dto.getUserid());

        Set<Role> userRoles = new HashSet<>();

        for (String roleName : dto.getRoles()) {
            ERole erole;

            try {
                erole = ERole.valueOf(roleName); // Converts string to enum
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role name: " + roleName);
            }

            Role role = roleRepository.findByName(erole)
                    .orElseThrow(() -> new RuntimeException("Role not found in DB: " + roleName));

            userRoles.add(role);
        }

        user.setRoles(userRoles);

        return userRepo.save(user);
    }


    // Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


    // Find user by ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new customException("User ID " + id + " not found."));
    }



}
