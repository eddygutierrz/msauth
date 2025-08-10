package com.impulsofirme.msauth.app.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.impulsofirme.msauth.app.dto.UserAuthDTO;

@Service
public class UserClient {
    @Value("${msusuarios.url}") private String baseUrl;
    @Value("${INTERNAL_AUTH_HEADER}") String internalSecret;
    private final RestTemplate rest;

    public UserClient(RestTemplateBuilder b) { this.rest = b.build(); }

    public UserAuthDTO findByUsername(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Auth", internalSecret); // nombre que definiste
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        var url = baseUrl + "/api/users/auth/" + UriUtils.encodePath(username, StandardCharsets.UTF_8);
        return rest.exchange(url, HttpMethod.GET, entity, UserAuthDTO.class).getBody();
    }
}