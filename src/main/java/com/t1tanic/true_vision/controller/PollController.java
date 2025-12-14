package com.t1tanic.true_vision.controller;

import com.t1tanic.true_vision.dto.poll.PollCreateRequest;
import com.t1tanic.true_vision.dto.poll.PollResponse;
import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.dto.poll.VoteRequest;
import com.t1tanic.true_vision.model.poll.Poll;
import com.t1tanic.true_vision.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/polls")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    /**
     * Creates a new poll in DRAFT status.
     */
    @PostMapping
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollCreateRequest request) {
        log.info("API Request: Create poll '{}'", request.title());
        Poll poll = pollService.createPoll(request.title(), request.question(), request.options());
        return new ResponseEntity<>(PollResponse.fromEntity(poll), HttpStatus.CREATED);
    }

    /**
     * Lists all polls that are currently ACTIVE for voting.
     */
    @GetMapping("/active")
    public ResponseEntity<List<PollResponse>> getActivePolls() {
        log.info("API Request: Get active polls");
        List<PollResponse> responses = pollService.findAllActivePolls().stream().map(PollResponse::fromEntity).toList();
        return ResponseEntity.ok(responses);
    }

    /**
     * Casts a vote for a specific option in a poll.
     * Note: In a real app, the userId would come from the Security Context (Token).
     */
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<String> vote(@PathVariable UUID pollId, @Valid @RequestBody VoteRequest request) {
        log.info("API Request: User {} voting in poll {}", request.userId(), pollId);
        pollService.castVote(pollId, request.optionId(), request.userId());
        return ResponseEntity.ok("Vote cast successfully.");
    }

    /**
     * Retrieves the aggregated results for a specific poll.
     */
    @GetMapping("/{pollId}/results")
    public ResponseEntity<PollResultResponse> getResults(@PathVariable UUID pollId) {
        log.info("API Request: Get results for poll {}", pollId);
        return ResponseEntity.ok(pollService.getPollResults(pollId));
    }

    /**
     * Publishes a poll, moving it from DRAFT to ACTIVE.
     * Maps to PATCH /api/v1/polls/{pollId}/publish
     */
    @PatchMapping("/{pollId}/publish")
    public ResponseEntity<PollResponse> publishPoll(@PathVariable UUID pollId) {
        log.info("API Request: Publish poll ID {}", pollId);
        Poll publishedPoll = pollService.publishPoll(pollId);
        return ResponseEntity.ok(PollResponse.fromEntity(publishedPoll));
    }
}
