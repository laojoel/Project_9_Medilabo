package com.medilabo.riskapplication.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Patient {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private List<String> notes;

    public int getAge() {
        if (dateOfBirth == null) {
            return -1;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    public char getGenderChar() {
        if (gender == null || gender.isBlank()) {
            return 'x';
        }
        char chr = gender.charAt(0);
        if (chr != 'F' && chr != 'M') {
            return 'x';
        }
        return chr;
    }
}
