package com.t1tanic.true_vision.repository;

import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;
import com.t1tanic.true_vision.model.poll.PollVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing PollVote entities.
 * Provides methods to check voting status and count votes for poll options.
 */
@Repository
public interface PollVoteRepository extends JpaRepository<PollVote, UUID> {

    /**
     * Counts the total number of votes cast in a specific poll.
     * Used for the dashboard summary.
     */
    long countByPollId(UUID pollId);

    /**
     * Checks if a user has already voted in a specific poll.
     * This is used to enforce the one-vote-per-user rule.
     * @param pollId The ID of the poll.
     * @param appUserId The ID of the user.
     * @return true if the user has voted in the poll, false otherwise.
     */
    boolean existsByPollIdAndAppUserId(UUID pollId, UUID appUserId);


    /**
     * Counts the number of votes for a specific poll option.
     * @param chosenOptionId The ID of the poll option.
     * @return The total number of votes for that option.
     */
    long countByChosenOptionId(UUID chosenOptionId);

    /**
     * Counts votes for an option filtered by a specific City District.
     */
    @Query("SELECT COUNT(v) FROM PollVote v WHERE v.chosenOption.id = :optionId " +
            "AND v.appUser.addressInfo.cityDistrict = :district")
    long countByOptionAndDistrict(@Param("optionId") UUID optionId, @Param("district") CityDistrict district);

    /**
     * Counts votes for an option filtered by Age Range.
     */
    @Query("SELECT COUNT(v) FROM PollVote v WHERE v.chosenOption.id = :optionId " +
            "AND v.appUser.basicInfo.ageRange = :ageRange")
    long countByOptionAndAgeRange(@Param("optionId") UUID optionId, @Param("ageRange") AgeRange ageRange);

    /**
     * Counts total votes grouped by City District for a specific poll.
     * @param pollId The ID of the poll.
     * @return A list of Object arrays where each array contains a City District and the corresponding vote count.
     */
    @Query("SELECT v.appUser.addressInfo.cityDistrict, COUNT(v) FROM PollVote v WHERE v.poll.id = :pollId GROUP BY v.appUser.addressInfo.cityDistrict")
    List<Object[]> countTotalVotesByDistrict(@Param("pollId") UUID pollId);

    /**
     * Counts total votes grouped by Age Range for a specific poll.
     * @param pollId The ID of the poll.
     * @return A list of Object arrays where each array contains an Age Range and the corresponding vote count.
     */
    @Query("SELECT v.appUser.basicInfo.ageRange, COUNT(v) FROM PollVote v WHERE v.poll.id = :pollId GROUP BY v.appUser.basicInfo.ageRange")
    List<Object[]> countTotalVotesByAgeRange(@Param("pollId") UUID pollId);

    /**
     * Groups votes by the hour they were cast for a specific poll.
     * Returns a list of arrays: [Integer hour, Long count]
     */
    @Query("SELECT FUNCTION('HOUR', v.voteTimestamp), COUNT(v) " +
            "FROM PollVote v WHERE v.poll.id = :pollId " +
            "GROUP BY FUNCTION('HOUR', v.voteTimestamp) " +
            "ORDER BY FUNCTION('HOUR', v.voteTimestamp) ASC")
    List<Object[]> countVotesByHour(@Param("pollId") UUID pollId);
}
