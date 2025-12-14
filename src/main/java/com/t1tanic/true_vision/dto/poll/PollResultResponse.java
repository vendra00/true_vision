package com.t1tanic.true_vision.dto.poll;

import java.util.List;
import java.util.UUID;

public record PollResultResponse(
        UUID pollId,
        String pollTitle,
        List<OptionCount> results,
        long totalVotes
) {
    public record OptionCount(
            UUID optionId,
            String optionText,
            long count
    ) {}
}
