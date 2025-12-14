package com.t1tanic.true_vision.dto.poll;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PollCreateRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Question text is required")
        String question,

        @NotEmpty(message = "At least two options are required for a poll")
        List<String> options
) {}
