package com.medilabo.patientapplication.controller;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Patient>> allPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAll());
    }


    @GetMapping("{id}")
    public ResponseEntity<Patient> patientId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getById(id.intValue()));
    }

    @PostMapping("modify")
    public ResponseEntity<Patient> update(@RequestBody Patient patient) {
        Patient updatedPatient = patientService.update(patient);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }

    @PostMapping("create")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        Patient createdPatient = patientService.create(patient);
        return ResponseEntity.status(HttpStatus.OK).body(createdPatient);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        Boolean result = patientService.delete((int)id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
