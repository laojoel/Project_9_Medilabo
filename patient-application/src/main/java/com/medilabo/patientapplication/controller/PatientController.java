package com.medilabo.patientapplication.controller;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientController {
    private final PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient/list")
    public ResponseEntity<List<Patient>> findAllPatients() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientService.getAll());
    }

}
