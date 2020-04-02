package com.agatap.veshje.config;

import com.agatap.veshje.model.User;
import com.agatap.veshje.model.UserRole;
import com.agatap.veshje.service.UserService;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsAdapter implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.findUserByEmail(email);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(email)
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getUserRole()))
                    .build();
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User with login '" + email + "' does not exist!");
        }
    }
}
