package com.example.springsecurity.service;

import com.example.springsecurity.model.User;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider{

    private UserService userService;

    public JwtAuthenticationProvider(UserService userService){
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("Authentication provider is invoked");

        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());

        if(userDetails.getUsername().equals(authentication.getName()) && authentication.getCredentials().equals(userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(authentication.getName(),authentication.getCredentials(),authentication.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
