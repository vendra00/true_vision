package com.t1tanic.true_vision.dto.poll;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record VoteRequest(
        @NotNull UUID optionId,
        @NotNull UUID userId
) {}
