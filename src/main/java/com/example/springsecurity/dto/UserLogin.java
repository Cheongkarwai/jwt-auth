package com.example.springsecurity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLogin {

    private String username;

    private String password;
}
