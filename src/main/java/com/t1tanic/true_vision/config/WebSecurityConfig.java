package com.t1tanic.true_vision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures the security filter chain to allow unauthenticated access
     * to the registration endpoint.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                // 1. Disable CSRF (Common for stateless REST APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        // Allow POST requests to your registration endpoint without authentication
                        .requestMatchers("/api/v1/users/register").permitAll()
                        .requestMatchers("/api/v1/users").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll()
                        // Require authentication for any other request (good default security)
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
