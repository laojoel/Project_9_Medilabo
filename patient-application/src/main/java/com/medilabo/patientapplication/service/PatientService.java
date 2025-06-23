package com.medilabo.patientapplication.service;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return patients.orElse(null);
    }

    public Patient update(Patient patient) {
        if (!patientRepository.existsById((int)patient.getId())) {
            return null;
        }
        return patientRepository.save(patient);
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public boolean delete(int id) {
        patientRepository.deleteById(id);
        return !patientRepository.existsById(id);
    }
}
