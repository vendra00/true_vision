package com.t1tanic.true_vision.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class AppUser extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    /**
     * The secure, one-way hash of the user's DNI/NIE.
     * This is used to ensure one person registers only once (uniqueness check)
     * without storing their sensitive ID number in plain text.
     */
    @Column(name = "dni_nie_hash", unique = true, nullable = false, length = 100)
    private String dniNieHash;

    @Column(name = "is_validated", nullable = false)
    private boolean isValidated = true;

    @Embedded
    private AppUserEmail emailInfo;

    @Embedded
    private AppUserBasicInfo basicInfo;

    @Embedded
    private AppUserAddress  addressInfo;

    public AppUser(String dniNieHash, AppUserEmail emailInfo, AppUserBasicInfo basicInfo, AppUserAddress addressInfo) {
        this.dniNieHash = dniNieHash;
        this.emailInfo = emailInfo;
        this.basicInfo = basicInfo;
        this.addressInfo = addressInfo;
        this.isValidated = true; // For MVP, we set this to true. In a real flow, this would be false until validation is complete.
    }
}
