package com.t1tanic.true_vision.enums;

import lombok.Getter;

/**
 * Enumeration for different age ranges.
 */
@Getter
public enum AgeRange {
    A_18_25("18-25"),
    B_26_35("26-35"),
    C_36_50("36-50"),
    D_51_65("51-65"),
    E_66_PLUS("66+");

    private final String range;

    AgeRange(String range) {
        this.range = range;
    }
}
