package com.t1tanic.true_vision.enums;

import lombok.Getter;

/**
 * Enumeration for different countries.
 */
@Getter
public enum Country {
    SPAIN("Spain"),
    FRANCE("France"),
    GERMANY("Germany"),
    ITALY("Italy"),
    UNITED_KINGDOM("United Kingdom"),
    UNITED_STATES("United States"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    JAPAN("Japan"),
    CHINA("China");

    private final String country;

    Country(String country) {
        this.country = country;
    }
}
