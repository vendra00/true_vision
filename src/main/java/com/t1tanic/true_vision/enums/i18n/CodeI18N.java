package com.t1tanic.true_vision.enums.i18n;

import lombok.Getter;

/**
 * Enumeration for supported internationalization (i18n) language codes.
 */
@Getter
public enum CodeI18N {
    ENGLISH("en"),
    SPANISH("es"),
    CATALAN("ca");

    private final String code;

    CodeI18N(String code) {
        this.code = code;
    }
}
