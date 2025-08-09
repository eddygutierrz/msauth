package com.impulsofirme.msauth.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impulsofirme.msauth.app.components.JWTTokenProvider;

@RestController
@RequestMapping("/auth")
public class TokenController {
    private final JWTTokenProvider jwt;

    public TokenController(JWTTokenProvider jwt) { this.jwt = jwt; }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String bearer) {
        String token = jwt.resolveToken(bearer);
        return jwt.validateToken(token) ? ResponseEntity.ok().build()
                                        : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}