package com.t1tanic.true_vision.config;

import com.t1tanic.true_vision.dto.app_user.UserRegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Global handler for REST exceptions across all controllers.
 * Centralizes exception logic to keep controllers clean.
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class) // Applies to all classes annotated with @RestController
public class RestExceptionHandler {

    /**
     * Handles the IllegalStateException, typically used for business logic errors
     * like duplicate registration (DNI/NIE already exists). Maps to HTTP 409 Conflict.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<UserRegistrationResponse> handleIllegalStateException(IllegalStateException ex) {
        log.warn("Business logic violation (Conflict): {}", ex.getMessage());

        // Maps the exception to the standard response DTO with CONFLICT status
        UserRegistrationResponse response = new UserRegistrationResponse(
                null,
                "FAILURE",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409
    }

    /**
     * Handles exceptions caused by @Valid failing on the DTO (e.g., @NotBlank, @Email fail).
     * Maps to HTTP 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserRegistrationResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collects all validation errors for a clear message
        String defaultMessage = "Validation failed for request: ";

        String validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst().orElse("Unknown validation error.");

        log.warn("Input validation failed: {}", validationErrors);

        UserRegistrationResponse response = new UserRegistrationResponse(
                null,
                "ERROR",
                defaultMessage + validationErrors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    /**
     * Handles all other uncaught exceptions. Maps to HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserRegistrationResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled Internal Server Error:", ex);

        UserRegistrationResponse response = new UserRegistrationResponse(
                null,
                "ERROR",
                "An unexpected internal error occurred."
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    /**
     * Handles ResponseStatusException thrown directly from controllers (e.g., 404 Not Found).
     * This ensures correct status codes are maintained instead of defaulting to 500.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<UserRegistrationResponse> handleResponseStatusException(ResponseStatusException ex) {
        // Extract the original status and reason
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String reason = ex.getReason() != null ? ex.getReason() : "Resource error.";

        log.warn("REST API Status Error ({}): {}", status, reason);

        // Map the exception to the standard response DTO
        UserRegistrationResponse response = new UserRegistrationResponse(
                null,
                "FAILURE",
                reason
        );
        // Return the original HTTP status code
        return new ResponseEntity<>(response, status);
    }
}
