package com.medilabo.frontapplication.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Note {
    private String patId;

    private String patient;

    private List<String> notes;
}
