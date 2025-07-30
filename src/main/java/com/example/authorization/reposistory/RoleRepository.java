package com.example.authorization.reposistory;

import com.example.authorization.model.ERole;
import com.example.authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    Set<Role> findByNameIn(Set<String> names);
}