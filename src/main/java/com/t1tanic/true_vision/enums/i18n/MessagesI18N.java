package com.t1tanic.true_vision.enums.i18n;

import lombok.Getter;

@Getter
public enum MessagesI18N {
    POLL_CREATED("poll.created"),
    POLL_UPDATED("poll.updated"),
    POLL_DELETED("poll.deleted"),
    OPTION_ADDED("option.added"),
    OPTION_REMOVED("option.removed"),
    VOTE_CAST("vote.cast"),
    REPORT_GENERATED("report.generated");

    private final String key;

    MessagesI18N(String key) {
        this.key = key;
    }
}
