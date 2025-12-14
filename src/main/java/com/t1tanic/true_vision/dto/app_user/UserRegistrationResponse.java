package com.t1tanic.true_vision.dto.app_user;

import java.util.UUID;

/**
 * Record for user registration response (DTO).
 * Sent back to the client upon successful or failed registration.
 */
public record UserRegistrationResponse(
        UUID userId,
        String status, // SUCCESS, FAILURE, ERROR
        String message
) {}
