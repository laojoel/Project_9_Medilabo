package com.medilabo.patientapplication.repository;

import com.medilabo.patientapplication.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {
    public Optional<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName,
                                                                      LocalDate dateOfBirth);
}
