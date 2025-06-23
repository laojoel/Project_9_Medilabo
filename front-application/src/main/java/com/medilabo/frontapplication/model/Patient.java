package com.medilabo.frontapplication.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
public class Patient {
    private long id;

    @Size(min = 2, message = "first name must be 2 characters long or more")
    private String firstName;

    @Size(min = 2, message = "last name must be 2 characters long or more")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "[FM]", message = "Gender must be either 'F' or 'M'")
    @Size(min = 1, message = "Gender must be either 'F' or 'M'")
    @Size(max = 1, message = "Gender must be either 'F' or 'M'")
    private String gender;

    @Size(min = 5, message = "address must be 5 characters long or more")
    private String address;

    @Size(min = 5, message = "phone number must be 5 characters long or more")
    private String phoneNumber;

    private List<String> notes;


    public String getFullName() {
        return firstName + " " + lastName;
    }
}
