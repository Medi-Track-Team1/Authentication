package com.example.authorization.model;


import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{

    private static final long serialVersionUID = 1L;


    private User u;

    public UserPrincipal(User u) {
        this.u=u;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // No need to prefix again
                .collect(Collectors.toList());
    }



    @Override
    public String getPassword() {

        return u.getPassword();
    }

    @Override
    public String getUsername() {

        return u.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

}
