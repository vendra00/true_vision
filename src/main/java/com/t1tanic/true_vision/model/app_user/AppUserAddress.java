package com.t1tanic.true_vision.model.app_user;

import com.t1tanic.true_vision.enums.City;
import com.t1tanic.true_vision.enums.CityDistrict;
import com.t1tanic.true_vision.enums.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AppUserAddress {
    @Column(name = "street", nullable = false)
    private String street;
    @Enumerated(EnumType.STRING)
    @Column(name = "city", nullable = false)
    private City city;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;
    @Enumerated(EnumType.STRING)
    @Column(name = "city_district", nullable = false)
    private CityDistrict cityDistrict;

    public AppUserAddress(String street, City city, String state, String postalCode, Country country, CityDistrict cityDistrict) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.cityDistrict = cityDistrict;
    }
}
