package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.model.poll.Poll;
import com.t1tanic.true_vision.model.poll.PollVote;
import java.util.List;
import java.util.UUID;

public interface PollService {

    Poll createPoll(String title, String question, List<String> options);
    List<Poll> findAllActivePolls();

    /**
     * Casts a vote for a specific poll.
     * Includes the critical security check for uniqueness.
     */
    PollVote castVote(UUID pollId, UUID optionId, UUID userId);

    /**
     * Gets current results for a poll.
     * We will expand this later for demographic analysis.
     */
    PollResultResponse getPollResults(UUID pollId);

    /**
     * Publishes a poll, making it ACTIVE for voting.
     * @param pollId the poll to publish
     * @return the published poll
     */
    Poll publishPoll(UUID pollId);
}
