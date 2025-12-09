package com.t1tanic.true_vision.enums;

import lombok.Getter;

@Getter
public enum CityDistrict {
    CIUTAT_VELLA("Ciutat Vella"),
    EIXAMPLE("Eixample"),
    SANTS_MONTJUIC("Sants-Montjuïc"),
    LES_CORTS("Les Corts"),
    SARRIA_SANT_GERVASI("Sarrià-Sant Gervasi"),
    GRACIA("Gràcia"),
    HORTA_GUINARDO("Horta-Guinardó"),
    NOU_BARRIS("Nou Barris"),
    SANT_ANDREU("Sant Andreu"),
    SANT_MARTI("Sant Martí");

    private final String district;
    CityDistrict(String district) {
        this.district = district;
    }
}
