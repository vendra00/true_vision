package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.enums.PollStatus;
import com.t1tanic.true_vision.model.app_user.AppUser;
import com.t1tanic.true_vision.model.poll.*;
import com.t1tanic.true_vision.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollOptionRepository optionRepository;
    private final PollVoteRepository voteRepository;
    private final AppUserRepository userRepository;

    @Override
    @Transactional
    public PollVote castVote(UUID pollId, UUID optionId, UUID userId) {
        log.info("Attempting to cast vote. User: {}, Poll: {}", userId, pollId);

        // 1. SECURITY: Check if user already voted in this poll
        if (voteRepository.existsByPollIdAndAppUserId(pollId, userId)) {
            log.warn("Security Alert: User {} attempted to vote twice in poll {}", userId, pollId);
            throw new IllegalStateException("You have already participated in this poll.");
        }

        // 2. VALIDATION: Fetch User, Poll, and Option
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found."));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalStateException("Poll not found."));
        PollOption option = optionRepository.findById(optionId).orElseThrow(() -> new IllegalStateException("Invalid option chosen."));
        Instant now = Instant.now();

        // Check if it's too early
        if (poll.getStartDate() != null && now.isBefore(poll.getStartDate())) {
            throw new IllegalStateException("This poll has not started yet. It opens on: " + poll.getStartDate());
        }

        // Check if it's too late
        if (poll.getEndDate() != null && now.isAfter(poll.getEndDate())) {
            poll.setStatus(PollStatus.CLOSED); // Auto-close if we detect it's past due
            pollRepository.save(poll);
            throw new IllegalStateException("This poll is now closed. Participation ended on: " + poll.getEndDate());
        }

        // 3. PERSISTENCE: Save the unique vote
        PollVote vote = new PollVote(user, poll, option);
        return voteRepository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public PollResultResponse getPollResults(UUID pollId) {
        log.info("Fetching results for poll ID: {}", pollId);

        // 1. Verify Poll exists
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalStateException("Poll not found."));

        // 2. Map options to their counts
        List<PollResultResponse.OptionCount> optionCounts = poll.getOptions().stream()
                .map(option -> new PollResultResponse.OptionCount(
                        option.getId(),
                        option.getOptionText(),
                        voteRepository.countByChosenOptionId(option.getId())
                ))
                .toList();

        // 3. Calculate total
        long totalVotes = optionCounts.stream().mapToLong(PollResultResponse.OptionCount::count).sum();

        return new PollResultResponse(poll.getId(), poll.getTitle(), optionCounts, totalVotes);
    }

    @Override
    @Transactional
    public Poll publishPoll(UUID pollId) {
        log.info("publishPoll - Attempting to publish poll ID: {}", pollId);

        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalStateException("Poll not found."));

        if (poll.getStatus() != PollStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT polls can be published.");
        }

        if (poll.getOptions() == null || poll.getOptions().isEmpty()) {
            throw new IllegalStateException("Cannot publish a poll without options.");
        }

        poll.setStatus(PollStatus.ACTIVE);
        poll.setActive(true);

        log.info("publishPoll - Poll ID: {} is now ACTIVE", pollId);
        return pollRepository.save(poll);
    }

    @Override
    @Transactional
    public Poll createPoll(String title, String question, List<String> options) {
        log.info("createPoll - Creating new poll with title: {}", title);

        // 1. Initialize the Poll entity
        // Using the constructor we defined in the Poll model
        Poll poll = new Poll(title, question, null, null); // Start/End dates can be set via Update later
        poll.setStatus(PollStatus.DRAFT);
        poll.setActive(false);

        // 2. Convert List<String> into List<PollOption> and link to the poll
        List<PollOption> pollOptions = options.stream()
                .map(optionText -> {
                    PollOption option = new PollOption();
                    option.setOptionText(optionText);
                    option.setPoll(poll); // Maintain the bidirectional relationship
                    return option;
                })
                .toList();

        poll.setOptions(pollOptions);

        // 3. Save the Poll (CascadeType.ALL will automatically save the options)
        Poll savedPoll = pollRepository.save(poll);

        log.info("createPoll - Successfully created poll with ID: {} and {} options", savedPoll.getId(), savedPoll.getOptions().size());

        return savedPoll;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> findAllActivePolls() {
        log.info("findAllActivePolls - Fetching active polls directly from database");
        return pollRepository.findByStatusWithAttributes(PollStatus.ACTIVE);
    }

}
