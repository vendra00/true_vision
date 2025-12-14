package com.t1tanic.true_vision.controller;

import com.t1tanic.true_vision.dto.app_user.AppUserResponse;
import com.t1tanic.true_vision.dto.app_user.UserRegistrationRequest;
import com.t1tanic.true_vision.dto.app_user.UserRegistrationResponse; // We will create this DTO next
import com.t1tanic.true_vision.dto.app_user.UserUpdateRequest;
import com.t1tanic.true_vision.model.app_user.AppUser;
import com.t1tanic.true_vision.service.AppUserService; // Use the interface
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.UUID;

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
     * <p>
     * This method receives a validated DTO, delegates registration and uniqueness
     * check to the service layer, and returns a success response. Business logic
     * exceptions (e.g., duplicate registration) are handled by the global
     * {@code RestExceptionHandler}.
     *
     * @param request The {@code UserRegistrationRequest} DTO containing all user details.
     * It is automatically validated via the {@code @Valid} annotation.
     * @return A {@code ResponseEntity} containing the {@code UserRegistrationResponse} DTO
     * with HTTP Status 201 (Created) upon successful registration.
     * @throws IllegalStateException (Handled Globally) If a user with the given DNI/NIE is already registered.
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("API Request received for new user registration. DNI/NIE: {}", request.dniNie());
        AppUser registeredUser = userService.registerNewUser(request);
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
     *
     * @return A {@code ResponseEntity} containing a list of {@code AppUserResponse} DTOs
     * (with HTTP Status 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        log.info("API Request received to fetch all registered users.");
        List<AppUser> users = userService.findAllUsers();
        log.info("Successfully fetched {} users.", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a single validated user by their unique UUID identifier.
     * This endpoint is mapped to GET /api/v1/users/{id}.
     *
     * @param id The UUID of the user to find (taken from the URL path).
     * @return A {@code ResponseEntity} containing the {@code AppUserResponse} DTO
     * (with HTTP Status 200 OK) if the user is found.
     * @throws ResponseStatusException with HTTP Status 404 (Not Found) if
     * no user with the given ID exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> getUserById(@PathVariable UUID id) {
        log.info("API Request received to fetch user by ID: {}", id);
        AppUser user = userService.findUserById(id)
                .orElseThrow(() -> {
                    String safeId = HtmlUtils.htmlEscape(id.toString());
                    log.warn("User not found for ID: {}", safeId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + safeId);
                });
        log.info("Successfully fetched user with ID: {}", id);
        return new ResponseEntity<>(AppUserResponse.fromEntity(user), HttpStatus.OK);
    }

    /**
     * Updates an existing user's mutable information (e.g., email, address, basic info).
     * <p>
     * This method receives a validated DTO and delegates the update logic to the service layer.
     * It relies on JPA Auditing to automatically update the 'updatedAt' timestamp.
     * Mapped to PUT /api/v1/users/{id}.
     *
     * @param id The UUID of the user to update (from the URL path).
     * @param request The {@code UserUpdateRequest} DTO containing the new data.
     * It is automatically validated via the {@code @Valid} annotation.
     * @return A {@code ResponseEntity} containing the updated {@code AppUserResponse} DTO
     * (with HTTP Status 200 OK).
     * @throws IllegalStateException (Handled Globally) If no user with the given ID exists.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponse> updateUserInfo(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
        log.info("API Request received to update user ID: {}", id);
        AppUser updatedUser = userService.updateUserInfo(id, request);
        log.info("User successfully updated with ID: {}", id);
        return new ResponseEntity<>(AppUserResponse.fromEntity(updatedUser), HttpStatus.OK);
    }

    /**
     * Deletes a registered user permanently based on their UUID.
     * <p>
     * This method delegates the deletion logic to the service layer.
     * Mapped to DELETE /api/v1/users/{id}.
     *
     * @param id The UUID of the user to delete (from the URL path).
     * @return A {@code ResponseEntity} with HTTP Status 204 (No Content)
     * upon successful deletion.
     * @throws IllegalStateException (Handled Globally) If no user with the given ID exists.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("API Request received to delete user with ID: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}