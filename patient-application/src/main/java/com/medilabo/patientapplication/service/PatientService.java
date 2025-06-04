package com.medilabo.patientapplication.service;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

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

    public Patient update(Patient patient) {
        if (!patientRepository.existsById((int)patient.getId())) {
            log.error("Patient with id " + patient.getId() + " not found for update");
            return null;
        }
        return patientRepository.save(patient);
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }
}
