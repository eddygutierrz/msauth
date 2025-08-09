package com.impulsofirme.msauth.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String enabled;
    private String token;

    public UserAuthDTO(Long id, String username, String password, String role, String enabled, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.token = token;
    }
}