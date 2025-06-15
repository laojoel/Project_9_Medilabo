package com.medilabo.frontapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class Note {

    private String id;
    private long patId;

    @Size(min = 4, message = "full name must be 4 characters long or more")
    private String patient;

    @JsonProperty("note")
    @Size(min = 1, message = "note cannot be empty")
    private String content;
}
