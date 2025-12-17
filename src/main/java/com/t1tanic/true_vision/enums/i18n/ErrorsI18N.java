package com.t1tanic.true_vision.enums.i18n;

import lombok.Getter;

/**
 * Enumeration for error message keys used in internationalization (i18n).
 */
@Getter
public enum ErrorsI18N {
    // Errores de Encuesta
    POLL_NOT_FOUND("poll.not.found"),
    POLL_TOO_EARLY("poll.too.early"),
    POLL_TOO_LATE("poll.too.late"),

    // Errores de Votación
    VOTE_DUPLICATE("vote.duplicate"),
    INVALID_OPTION("option.invalid"),

    // Errores de Usuario
    USER_NOT_FOUND("user.not.found"),
    USER_ALREADY_EXISTS("user.exists"),

    // Errores Genéricos
    INTERNAL_ERROR("internal.error"),
    VALIDATION_FAILED("validation.failed");

    private final String key;

    ErrorsI18N(String key) {
        this.key = key;
    }
}
