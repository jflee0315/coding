package com.sidecarhealth.assignmemt.coding.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("demo", "$2a$10$8rKeBCWLHauWO.33usLCBuwbsUoITqHHk2cLU/MvM3cgCCF132nWy", new ArrayList<>());
    }
}
