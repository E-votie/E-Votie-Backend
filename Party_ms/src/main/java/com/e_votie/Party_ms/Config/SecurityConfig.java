package com.e_votie.Party_ms.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain");

        http
                // Disable CSRF for stateless APIs
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(withDefaults())

                // Authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/party/**").permitAll()  // Allow unauthenticated access to party APIs
                        .requestMatchers("/api/voter/**").permitAll()  // Allow unauthenticated access to voter APIs
                        .requestMatchers("/api/document/**").permitAll()  // Allow unauthenticated access to document APIs
                        .anyRequest().authenticated()                 // Require authentication for all other requests
                )

                // OAuth2 Resource Server configuration
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );

        log.info("Configuring SecurityFilterChain complete");
        return http.build();
    }
}