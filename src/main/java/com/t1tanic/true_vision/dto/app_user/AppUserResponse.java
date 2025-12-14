package com.t1tanic.true_vision.dto.app_user;

import com.t1tanic.true_vision.model.app_user.AppUser;
import com.t1tanic.true_vision.model.app_user.AppUserAddress;
import com.t1tanic.true_vision.model.app_user.AppUserBasicInfo;
import com.t1tanic.true_vision.model.app_user.AppUserEmail;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for sending user data back to the client (Read/Update response).
 * Excludes sensitive data like the DNI/NIE hash.
 */
public record AppUserResponse(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        boolean isValidated,

        // Embedded Information
        AppUserEmail emailInfo,
        AppUserBasicInfo basicInfo,
        AppUserAddress addressInfo
) {
    // Custom factory method to map the internal entity to the safe response DTO
    public static AppUserResponse fromEntity(AppUser user) {
        return new AppUserResponse(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isValidated(),
                user.getEmailInfo(),
                user.getBasicInfo(),
                user.getAddressInfo()
        );
    }
}
