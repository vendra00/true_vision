package com.t1tanic.true_vision.model;

/**
 * A utility class/interface to hold regex patterns as compile-time constants
 * for use in Java annotations (e.g., @Pattern).
 */
public final class RegexConstants {

    // 1. DNI/NIE Pattern
    public static final String DNI_NIE_REGEX =
            "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$|^X[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]$|^Y[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]$|^Z[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]$";

    // 2. Postal Code Pattern
    public static final String POSTAL_CODE_REGEX =
            "^[0-9]{5}$";

    // Private constructor prevents instantiation of this utility class
    private RegexConstants() {
        throw new IllegalStateException("Utility class");
    }
}
