package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.model.poll.Poll;
import com.t1tanic.true_vision.model.poll.PollVote;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing polls and votes.
 * Defines methods for creating polls, casting votes, and retrieving poll results.
 */
public interface PollService {

    /**
     * Creates a new poll with the given title, question, and options.
     * @param title The title of the poll
     * @param question The question being asked in the poll
     * @param options The list of options for the poll
     * @return the created Poll
     */
    Poll createPoll(String title, String question, List<String> options);

    /**
     * Finds all active polls available for voting.
     * @return list of active Polls
     */
    List<Poll> findAllActivePolls();

    /**
     * Casts a vote for a given poll option by a user.
     * @param pollId Poll ID
     * @param optionId Option ID
     * @param userId User ID
     * @return the recorded PollVote
     */
    PollVote castVote(UUID pollId, UUID optionId, UUID userId);

    /**
     * Retrieves the results of a poll, including total votes and breakdowns by demographics.
     * @param pollId the poll ID
     * @return the poll results
     */
    PollResultResponse getPollResults(UUID pollId);

    /**
     * Publishes a poll, making it ACTIVE for voting.
     * @param pollId the poll to publish
     * @return the published poll
     */
    Poll publishPoll(UUID pollId);
}
