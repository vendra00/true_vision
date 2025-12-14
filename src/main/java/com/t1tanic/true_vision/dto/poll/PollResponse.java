package com.t1tanic.true_vision.dto.poll;

import com.t1tanic.true_vision.model.poll.Poll;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PollResponse(
        UUID id,
        String title,
        String question,
        String status,
        Instant startDate,
        Instant endDate,
        List<OptionResponse> options
) {
    public record OptionResponse(UUID id, String text) {
    }

    public static PollResponse fromEntity(Poll poll) {
        return new PollResponse(
                poll.getId(),
                poll.getTitle(),
                poll.getQuestion(),
                poll.getStatus().name(),
                poll.getStartDate(),
                poll.getEndDate(),
                poll.getOptions().stream()
                        .map(opt -> new OptionResponse(opt.getId(), opt.getOptionText()))
                        .toList()
        );
    }
}
