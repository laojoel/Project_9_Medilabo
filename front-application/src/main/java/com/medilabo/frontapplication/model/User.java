package com.medilabo.frontapplication.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class User {
    @Size(min = 3, message = "Username must be more than 3 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters")
    private String username;
    @Size(min = 3, message = "Password can't be empty and must be more than 3 characters")
    private String password;

    private String message;
    private boolean isAuthenticated = false;

}
