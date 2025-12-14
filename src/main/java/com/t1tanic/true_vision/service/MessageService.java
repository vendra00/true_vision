package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.enums.i18n.ErrorsI18N;
import com.t1tanic.true_vision.enums.i18n.MessagesI18N;

/**
 * Interface for localized message resolution.
 */
public interface MessageService {

    /**
     * Resolves a localized informational message.
     */
    String getMessage(MessagesI18N message, Object... args);

    /**
     * Resolves a localized error message.
     */
    String getMessage(ErrorsI18N error, Object... args);
}
