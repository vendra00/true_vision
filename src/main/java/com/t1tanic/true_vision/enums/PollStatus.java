package com.t1tanic.true_vision.enums;

import lombok.Getter;

/**
 * Enumeration for different poll statuses.
 */
@Getter
public enum PollStatus {
    ACTIVE("Active"),
    ARCHIVED("Archived"),
    CLOSED("Closed"),
    DRAFT("Draft");

    private final String status;

    PollStatus(String status) {
        this.status = status;
    }
}
