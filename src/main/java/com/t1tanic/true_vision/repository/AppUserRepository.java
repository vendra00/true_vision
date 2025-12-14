package com.t1tanic.true_vision.repository;

import com.t1tanic.true_vision.model.app_user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    /**
     * Finds if a user exists in the database by their DNI/NIE hash.
     * This method is crucial for the uniqueness check during registration.
     * Spring Data JPA will automatically implement the necessary SQL query.
     * * @param dniNieHash The secure, one-way hash of the DNI/NIE.
     * @return true if a user with that hash exists, false otherwise.
     */
    boolean existsByDniNieHash(String dniNieHash);
}
