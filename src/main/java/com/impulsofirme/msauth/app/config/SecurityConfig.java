package com.impulsofirme.msauth.app.config;

import java.time.Duration;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
      // Desactiva CSRF (o ignora el path de auth)
      .csrf(csrf -> csrf.disable())
      // .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**"))

      // CORS
      .cors(c -> c.configurationSource(corsConfigurationSource()))

      // Permisos
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers("/api/auth/**").permitAll()
        .anyRequest().authenticated()
      );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // Orígenes EXACTOS
        cfg.setAllowedOrigins(List.of(
            "https://admin-portal.impulsofirme.com.mx",
            "http://localhost:4200"  // solo para dev
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","Cache-Control"));
        // Con JWT en Authorization NO necesitamos cookies:
        cfg.setAllowCredentials(false);
        cfg.setMaxAge(Duration.ofHours(1));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}