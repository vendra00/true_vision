package com.t1tanic.true_vision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for security-related beans.
 *
 * @author Gabriel Vendramini
 */
@Configuration
public class SecurityConfig {

    /**
     * Defines the PasswordEncoder bean.
     * The BCryptPasswordEncoder is a secure, slow hashing algorithm with built-in salting,
     * making it ideal for creating secure, unique identifiers from sensitive input
     * like the DNI/NIE.
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
