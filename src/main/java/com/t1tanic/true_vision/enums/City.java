package com.t1tanic.true_vision.enums;

import lombok.Getter;

@Getter
public enum City {
    // Cities in Catalonia, Spain
    BARCELONA("Barcelona"),
    LHOSPITALET_DE_LLOBREGAT("L'Hospitalet de Llobregat"),
    BADALONA("Badalona"),
    SANT_CUGAT_DEL_VALLES("Sant Cugat del Vallès"),
    SABADELL("Sabadell"),
    TERRASSA("Terrassa"),
    MATARO("Mataró"),
    GRANOLLERS("Granollers"),
    SANT_BOI_DE_LLOBREGAT("Sant Boi de Llobregat"),
    VILANOVA_I_LA_GELTRU("Vilanova i la Geltrú"),
    OTHER("Other");

    private final String city;

    City(String city) {
        this.city = city;
    }
}
