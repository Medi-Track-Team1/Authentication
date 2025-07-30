package com.example.authorization.config;

import com.example.authorization.model.ERole;
import com.example.authorization.model.Role;
import com.example.authorization.reposistory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (ERole erole : ERole.values()) {
            if (roleRepository.findByName(erole).isEmpty()) {
                Role role = new Role();
                role.setName(erole);
                roleRepository.save(role);
            }
        }
    }
}
