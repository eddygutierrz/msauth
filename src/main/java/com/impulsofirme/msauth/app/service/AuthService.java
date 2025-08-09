package com.impulsofirme.msauth.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.impulsofirme.msauth.app.components.JWTTokenProvider;
import com.impulsofirme.msauth.app.dto.LoginRequest;
import com.impulsofirme.msauth.app.dto.LoginResponse;
import com.impulsofirme.msauth.app.dto.UserAuthDTO;

@Service
public class AuthService {
    private final UserClient userClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwt;

    public AuthService(UserClient userClient,
                       BCryptPasswordEncoder passwordEncoder,
                       JWTTokenProvider jwt) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public LoginResponse login(LoginRequest req) {
        UserAuthDTO u = userClient.obtenerUsuarioPorUsername(req.getUsername());
        if (u == null || u.getEnabled() == null || "INACTIVE".equalsIgnoreCase(u.getEnabled())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String token = jwt.generateToken(u.getUsername(), u.getRole());
        return new LoginResponse(token, u.getUsername(), u.getRole());
    }
}