package com.medilabo.frontapplication.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@Setter
public class Patient {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private char gender;
    private String address;
    private String phoneNumber;
}
