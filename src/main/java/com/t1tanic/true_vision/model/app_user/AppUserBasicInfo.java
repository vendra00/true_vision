package com.t1tanic.true_vision.model.app_user;

import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.Country;
import com.t1tanic.true_vision.enums.Gender;
import com.t1tanic.true_vision.enums.Nationality;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AppUserBasicInfo {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality", nullable = false)
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "place_of_birth", nullable = false)
    private Country placeOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_range", nullable = false)
    private AgeRange ageRange;

    public AppUserBasicInfo(String firstName, String middleName, String lastName, Gender gender, LocalDate dob, Nationality nationality, Country placeOfBirth, AgeRange ageRange) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.placeOfBirth = placeOfBirth;
        this.ageRange = ageRange;
    }
}
