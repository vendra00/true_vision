package com.t1tanic.true_vision.repository;

import com.t1tanic.true_vision.model.poll.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing PollOption entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, UUID> {}
