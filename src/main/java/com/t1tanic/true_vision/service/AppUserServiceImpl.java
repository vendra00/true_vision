package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.UserRegistrationRequest;
import com.t1tanic.true_vision.model.AppUser;
import com.t1tanic.true_vision.model.AppUserAddress;
import com.t1tanic.true_vision.model.AppUserBasicInfo;
import com.t1tanic.true_vision.model.AppUserEmail;
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
        return Optional.empty();
    }

    @Override
    public List<AppUser> findAllUsers() {
        log.info("findAllUsers - Start finding all users.");
        return userRepository.findAll();
    }

    @Override
    public AppUser updateUserInfo(UUID id, AppUserBasicInfo basicInfo, AppUserAddress addressInfo) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }
}
