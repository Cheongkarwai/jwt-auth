package com.example.springsecurity.dto;

import com.example.springsecurity.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken {

    @JsonProperty("username")
    private String username;

    @JsonProperty("access_token")
    private String accessToken;

    private List<Role> scope;

}
