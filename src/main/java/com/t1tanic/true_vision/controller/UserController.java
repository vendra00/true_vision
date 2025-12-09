package com.t1tanic.true_vision.controller;

import com.t1tanic.true_vision.dto.UserRegistrationRequest;
import com.t1tanic.true_vision.dto.UserRegistrationResponse; // We will create this DTO next
import com.t1tanic.true_vision.model.AppUser;
import com.t1tanic.true_vision.service.AppUserService; // Use the interface
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users") // Base path for all user-related endpoints
public class UserController {
    private final AppUserService userService;

    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    /**
     * Handles the user registration and DNI/NIE validation process.
     * Maps to POST /api/v1/users/register
     * * @param request The registration data validated by the @Valid annotation.
     * @return A ResponseEntity with the new user ID and status.
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        // 1. Logging the incoming request (DNI/NIE only, no sensitive data)
        log.info("API Request received for new user registration. DNI/NIE: {}", request.dniNie());

        // 2. Call the service (Exceptions like IllegalStateException are now handled globally)
        AppUser registeredUser = userService.registerNewUser(request);

        // 3. Logging the success and preparing the clean response
        log.info("User registered successfully with ID: {}", registeredUser.getId());

        UserRegistrationResponse response = new UserRegistrationResponse(
                registeredUser.getId(),
                "SUCCESS",
                "User successfully validated and registered."
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all registered users.
     * Maps to GET /api/v1/users
     * WARNING: This endpoint exposes all user data and should be secured (e.g., restricted to ADMIN roles)
     * * @return A list of AppUser entities (WARNING: Exposes internal model data).
     */
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        log.info("API Request received to fetch all registered users.");
        List<AppUser> users = userService.findAllUsers();
        log.info("Successfully fetched {} users.", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}