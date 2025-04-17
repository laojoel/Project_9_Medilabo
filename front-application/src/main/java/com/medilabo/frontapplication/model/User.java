package com.medilabo.frontapplication.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class User {
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be more than 3 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only alphabetic characters")
    @Pattern(regexp = "^\\p{L}+$", message = "Username must contain only alphabetic UTF-8 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password must be more than 6 characters")
    @Pattern(regexp = "^\\p{L}+$", message = "Password must contain only alphabetic UTF-8 characters")
    private String password;

}
