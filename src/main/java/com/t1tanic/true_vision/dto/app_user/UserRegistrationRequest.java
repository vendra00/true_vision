package com.t1tanic.true_vision.dto.app_user;

import com.t1tanic.true_vision.enums.*;
import com.t1tanic.true_vision.model.RegexConstants;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Record for user registration request (DTO).
 * This provides an immutable, concise data holder for validated input.
 */
public record UserRegistrationRequest(
        @NotBlank(message = "DNI or NIE is required.")
        @Pattern(regexp = RegexConstants.DNI_NIE_REGEX, message = "DNI or NIE format is invalid.")
        String dniNie,
        @NotBlank(message = "Email is required.")
        @Email(message = "Email format is invalid.")
        String email,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Null String middleName,
        @NotNull Gender gender,
        @NotNull @Past(message = "Date of birth must be in the past.")
        LocalDate dob,
        @NotNull Nationality nationality,
        @NotNull Country placeOfBirth,
        @NotNull AgeRange ageRange,
        @NotBlank String street,
        @NotNull City city,
        @NotBlank String state,
        @NotBlank @Pattern(regexp = RegexConstants.POSTAL_CODE_REGEX, message = "Postal code must be 5 digits.")
        String postalCode,
        @NotNull Country country,
        @NotNull CityDistrict cityDistrict
) {}
