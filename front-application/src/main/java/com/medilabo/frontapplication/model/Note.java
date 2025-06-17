package com.medilabo.frontapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class Note {

    @Id
    private String id;
    private long patId;

    @Size(min = 4, message = "patient's full name must be 4 characters long or more")
    private String patient;

    @JsonProperty("note")
    @NotBlank(message = "note cannot be empty")
    private String content;
}
