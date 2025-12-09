package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.UserRegistrationRequest;
import com.t1tanic.true_vision.model.AppUser;
import com.t1tanic.true_vision.model.AppUserAddress;
import com.t1tanic.true_vision.model.AppUserBasicInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface defining the business logic contract for AppUser operations (CRUD).
 * The DTOs will be introduced here once they are defined.
 */
public interface AppUserService {

    /**
     * Registers a new validated user, performing uniqueness checks, using a DTO.
     * @param request The complete, validated data transfer object from the client.
     * @return The newly registered AppUser entity.
     */
    AppUser registerNewUser(UserRegistrationRequest request);

    /**
     * Finds a user by their unique UUID identifier.
     */
    Optional<AppUser> findUserById(UUID id);

    /**
     * Retrieves a list of all registered AppUsers.
     */
    List<AppUser> findAllUsers();

    /**
     * Updates an existing user's basic or address information.
     * NOTE: This will require a specific DTO for update requests later.
     */
    AppUser updateUserInfo(UUID id, AppUserBasicInfo basicInfo, AppUserAddress addressInfo);

    /**
     * Deletes a user permanently based on their UUID.
     */
    void deleteUser(UUID id);
}
