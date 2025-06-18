package com.medilabo.riskapplication.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Note {
    private String id;
    private long patId;
    private String patient;
    @JsonProperty("note")
    private String content;
}
