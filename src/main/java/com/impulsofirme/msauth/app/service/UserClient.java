package com.impulsofirme.msauth.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.impulsofirme.msauth.app.dto.UserAuthDTO;

@Service
public class UserClient {
    @Value("${msusuarios.url}") private String msUsuariosUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public UserAuthDTO obtenerUsuarioPorUsername(String username) {
        String url = msUsuariosUrl + "/auth/" + username;
        HttpHeaders h = new HttpHeaders();
        h.set("X-Internal-Auth", System.getenv("INTERNAL_AUTH_HEADER"));
        HttpEntity<Void> req = new HttpEntity<>(h);
        return restTemplate.exchange(url, HttpMethod.GET, req, UserAuthDTO.class).getBody();
    }
}