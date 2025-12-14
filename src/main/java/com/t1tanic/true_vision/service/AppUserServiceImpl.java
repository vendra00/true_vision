package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.app_user.UserRegistrationRequest;
import com.t1tanic.true_vision.dto.app_user.UserUpdateRequest;
import com.t1tanic.true_vision.model.app_user.AppUser;
import com.t1tanic.true_vision.model.app_user.AppUserAddress;
import com.t1tanic.true_vision.model.app_user.AppUserBasicInfo;
import com.t1tanic.true_vision.model.app_user.AppUserEmail;
import com.t1tanic.true_vision.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AppUser registerNewUser(UserRegistrationRequest request) {
        log.info("registerNewUser - Start registration process for DNI/NIE: {}", request.dniNie());

        // 1. GENERATE UNIQUE IDENTIFIER (Secure Hash of DNI/NIE)
        // Accessing the record component using the method call dniNie()
        String dniNieHash = passwordEncoder.encode(request.dniNie());
        log.info("registerNewUser - Generated DNI/NIE hash for uniqueness check.");

        // 2. CHECK FOR DUPLICATE REGISTRATION
        if (userRepository.existsByDniNieHash(dniNieHash)) {
            throw new IllegalStateException("User with this DNI/NIE is already registered. Only one registration per ID is allowed.");
        }
        log.info("registerNewUser - No duplicate found. Proceeding with user creation.");
        // 3. MAP DTO TO EMBEDDED OBJECTS

        AppUserEmail emailInfo = new AppUserEmail(request.email());

        AppUserBasicInfo basicInfo = new AppUserBasicInfo(
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.gender(),
                request.dob(),
                request.nationality(),
                request.placeOfBirth(),
                request.ageRange()
        );

        AppUserAddress addressInfo = new AppUserAddress(
                request.street(),
                request.city(),
                request.state(),
                request.postalCode(),
                request.country(),
                request.cityDistrict()
        );

        AppUser newUser = new AppUser(dniNieHash, emailInfo, basicInfo, addressInfo);

        log.info("registerNewUser - New user entity created. Saving to database.");
        return userRepository.save(newUser);
    }

    @Override
    public Optional<AppUser> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<AppUser> findAllUsers() {
        log.info("findAllUsers - Start finding all users.");
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public AppUser updateUserInfo(UUID id, UserUpdateRequest request) {

        log.info("updateUserInfo - Starting comprehensive update for user ID: {}", id);

        // 1. Find the existing user
        AppUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("updateUserInfo - Update failed: User not found with ID: {}", id);
                    // Throwing IllegalStateException is caught by the RestExceptionHandler (409 Conflict)
                    return new IllegalStateException("User not found with ID: " + id);
                });

        // 2. Map DTO fields to new embedded objects

        // A. Update Email Info
        // Note: We don't change the email verified status automatically here.
        AppUserEmail updatedEmailInfo = new AppUserEmail(request.email());
        updatedEmailInfo.setEmailVerified(existingUser.getEmailInfo().isEmailVerified()); // Preserve verification status

        // B. Update Basic Info (demographics and PII)
        AppUserBasicInfo updatedBasicInfo = new AppUserBasicInfo(
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.gender(),
                request.dob(),
                request.nationality(),
                request.placeOfBirth(),
                request.ageRange()
        );

        // C. Update Address Info
        AppUserAddress updatedAddressInfo = new AppUserAddress(
                request.street(),
                request.city(),
                request.state(),
                request.postalCode(),
                request.country(),
                request.cityDistrict()
        );

        // 3. Apply updates to the existing entity
        existingUser.setEmailInfo(updatedEmailInfo);
        existingUser.setBasicInfo(updatedBasicInfo);
        existingUser.setAddressInfo(updatedAddressInfo);

        // 4. Save the entity (JPA Auditing updates 'updatedAt')
        log.info("updateUserInfo - User ID {} successfully updated. Saving changes.", id);
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        log.info("deleteUser - Attempting to delete user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("deleteUser - Deletion failed: User not found with ID: {}", id);
            throw new IllegalStateException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("deleteUser - User successfully deleted with ID: {}", id);
    }
}
