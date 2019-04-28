package com.gmail.salahub.nikolay.service.impl;

import com.gmail.salahub.nikolay.service.UserService;
import com.gmail.salahub.nikolay.service.model.UserDTO;
import com.gmail.salahub.nikolay.service.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User is not found");
        }
        return new UserPrincipal(user);
    }
}
