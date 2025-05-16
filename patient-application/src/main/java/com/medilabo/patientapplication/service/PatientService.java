package com.medilabo.patientapplication.service;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Integer id) {
        Optional<Patient> patients = patientRepository.findById(id);
        if (patients.isEmpty()) {
            log.error("Patient with id " + id + " not found");
            return null;
        }
        return patients.get();
    }
}
