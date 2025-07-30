package com.example.authorization.service;



import com.example.authorization.model.User;
import com.example.authorization.model.UserPrincipal;
import com.example.authorization.reposistory.userreposistory;
import com.example.authorization.utils.customException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private userreposistory repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User u= repo.findByEmail(username).orElse(null);

        if (u==null) {
            System.out.println("User 404");
            throw  new customException("user not found");
        }
        return new UserPrincipal(u);
    }



}
