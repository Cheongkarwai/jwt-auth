package com.example.springsecurity.service;

import com.example.springsecurity.dto.AccessToken;
import com.example.springsecurity.dto.UserLogin;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {

        userRepository.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getAuthorities().stream()
                        .map(role->new Role(role.getAuthority()))
                        .collect(Collectors.toList()))
                .isEnabled(user.isEnabled())
                .build());
    }

    @Override
    public void updateUser(UserDetails user) {

        userRepository.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getAuthorities().stream()
                        .map(role->new Role(role.getAuthority()))
                        .collect(Collectors.toList()))
                .isEnabled(user.isEnabled())
                .build());
    }

    @Override
    public void deleteUser(String username) {

        userRepository.deleteById(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {


    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow();
    }

    public AccessToken login(UserLogin userLogin){
        if(userExists(userLogin.getUsername())){
            UserDetails userDetails = loadUserByUsername(userLogin.getUsername());
            if(passwordEncoder.matches(userLogin.getPassword(),userDetails.getPassword())){
                return AccessToken.builder().accessToken(JwtUtil.generateToken(User.builder()
                                .username(userDetails.getUsername())
                                .password(userDetails.getPassword())
                                .roles(userDetails.getAuthorities().stream()
                                        .map(role->new Role(role.getAuthority()))
                                        .collect(Collectors.toList()))
                                .build()))
                        .username(userDetails.getUsername())
                        .scope(userDetails.getAuthorities().stream()
                                .map(role->new Role(role.getAuthority()))
                                .collect(Collectors.toList()))
                        .build();
            }
        }

        throw new BadCredentialsException("User not found");
    }

}
