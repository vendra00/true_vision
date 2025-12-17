package com.t1tanic.true_vision.enums.i18n;

import lombok.Getter;

/**
 * Enumeration for locating internationalization (i18n) resource files.
 */
@Getter
public enum LocatorI18N {
    MESSAGES("i18n/messages"),
    ERRORS("i18n/errors");

    private final String path;

    LocatorI18N(String path) {
        this.path = path;
    }
}
