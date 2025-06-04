package com.medilabo.patientapplication.controller;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public ResponseEntity<List<Patient>> allPatients() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientService.getAll());
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<Patient> patientId(@PathVariable("id") Long id) {
        System.out.println("View ID " + id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientService.getById(id.intValue()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Patient> update(@RequestBody Patient patient) {
        System.out.println("Updating ID " + patient.getId());
        Patient updatedPatient = patientService.update(patient);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }

    @PostMapping("/create")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        System.out.println("Creating " + patient.getFirstName() + " " + patient.getLastName());
        Patient createdPatient = patientService.create(patient);
        return ResponseEntity.status(HttpStatus.OK).body(createdPatient);
    }

}
