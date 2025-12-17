package com.t1tanic.true_vision.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Base entity class that provides auditing fields for creation and last update timestamps.
 * Other entity classes can extend this class to inherit these common fields.
 */
@Getter
@Setter
@MappedSuperclass // Tells JPA not to create a table for this class, but to include its fields in subclasses
@EntityListeners(AuditingEntityListener.class) // Enables the automatic population of the auditing fields
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    // updatable = false ensures this field is set only once (on creation)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    // This field is automatically updated every time the entity is persisted (updated)
    private Instant updatedAt;
}
