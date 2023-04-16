package com.example.springsecurity.controller;

import com.example.springsecurity.dto.UserLogin;
import com.example.springsecurity.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/create")
    public void createUser(){

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserLogin userLogin){
        return ResponseEntity.ok(userService.login(userLogin));
    }


    @PostMapping("/register")
    public HttpEntity<?> register(){
        return null;
    }

    @PostMapping("/logout")
    public HttpEntity<?> logout(){
        return null;
    }


}
