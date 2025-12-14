package com.t1tanic.true_vision.repository;

import com.t1tanic.true_vision.enums.PollStatus;
import com.t1tanic.true_vision.model.poll.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll , UUID> {

    /**
     * "JOIN FETCH" tells Hibernate to grab the options in a single SQL query.
     * This prevents the LazyInitializationException when mapping to a DTO.
     */
    @Query("SELECT DISTINCT p FROM Poll p LEFT JOIN FETCH p.options WHERE p.status = :status")
    List<Poll> findByStatusWithAttributes(@Param("status") PollStatus status);
}
