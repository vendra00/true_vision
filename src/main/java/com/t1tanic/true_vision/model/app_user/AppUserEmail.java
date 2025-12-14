package com.t1tanic.true_vision.model.app_user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AppUserEmail {
    /**
     * The user's primary email address. It should be unique across all users.
     */
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    // We can add fields like 'isVerified' or 'verificationTimestamp' here later.
    @Column(name = "is_email_verified", nullable = false)
    private boolean isEmailVerified = false; // Initial state is usually unverified

    // Custom Constructor
    public AppUserEmail(String email) {
        this.email = email;
        // In a real flow, this would be set to false and later updated upon clicking a verification link.
        // For the MVP, we can keep it as true if you skip email verification for now, or false if you plan to add it.
        this.isEmailVerified = true;
    }
}
