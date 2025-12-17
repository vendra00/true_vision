package com.t1tanic.true_vision.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA Auditing features.
 *
 * @author Gabriel Vendramini
 */
@Configuration
@EnableJpaAuditing // This is the single annotation that enables automatic auditing
public class JpaConfig {
    // This class is empty but its annotation is crucial for enabling JPA Auditing
}