package com.impulsofirme.msauth.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private final String token;
    private final String tokenType = "Bearer";
    private final String username;
    private final String role;
    
    public LoginResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }
}