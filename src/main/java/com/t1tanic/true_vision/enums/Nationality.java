package com.t1tanic.true_vision.enums;

import lombok.Getter;

/**
 * Enumeration for different nationalities.
 */
@Getter
public enum Nationality {
    SPANISH("Spanish"),
    COLOMBIAN("Colombian"),
    PERUVIAN("Peruvian"),
    GUATEMALAN("Guatemalan"),
    BRAZILIAN("Brazilian"),
    ARGENTINIAN("Argentinian"),
    FRENCH("French"),
    GERMAN("German"),
    ITALIAN("Italian"),
    BRITISH("British"),
    AMERICAN("American"),
    CANADIAN("Canadian"),
    AUSTRALIAN("Australian"),
    JAPANESE("Japanese"),
    CHINESE("Chinese"),
    OTHER("Other");

    private final String nationality;

    Nationality(String nationality) {
        this.nationality = nationality;
    }
}
