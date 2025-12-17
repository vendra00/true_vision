package com.t1tanic.true_vision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for True Vision.
 *
 * @author Gabriel Vendramini
 */
@EnableScheduling
@SpringBootApplication
public class TrueVisionApplication {
    static void main(String[] args) {
        SpringApplication.run(TrueVisionApplication.class, args);
    }
}
