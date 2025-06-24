package com.medilabo.patientapplication.service;

import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Should return a List of All patients
    @Test
    void testGetAll() {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setIdAndFullName(1, "john", "smith");
        Patient patient2 = new Patient();
        patient2.setIdAndFullName(2, "jane", "doe");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("john", result.getFirst().getFirstName());
        assertEquals("jane", result.get(1).getFirstName());
        verify(patientRepository, times(1)).findAll();
    }

    // Should return a Patient given a patient ID
    @Test
    void testGetById() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getById(1);

        // Assert
        assertNotNull(result);
        assertEquals("john", result.getFirstName());
        verify(patientRepository, times(1)).findById(1);
    }

    // should return Null as patient ID could not be found
    @Test
    void testGetById_NotFound() {
        // Arrange
        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Patient patient = patientService.getById(1);

        // Assert
        assertNull(patient);
        verify(patientRepository, times(1)).findById(1);
    }

    // should return the updated Patient, success.
    @Test
    void testUpdate() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        Patient modifiedPatient = new Patient();
        modifiedPatient.setIdAndFullName(1, "edit", "smith");

        when(patientRepository.existsById(1)).thenReturn(true);
        when(patientRepository.save(patient)).thenReturn(modifiedPatient);

        // Act
        Patient result = patientService.update(patient);

        // Assert
        assertNotNull(result);
        assertEquals("edit", result.getFirstName());
        verify(patientRepository, times(1)).existsById(1);
        verify(patientRepository, times(1)).save(patient);
    }

    // should return null as the Patient could not be found
    @Test
    void testUpdate_NotFound() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientRepository.existsById(1)).thenReturn(false);

        // Act
        Patient result = patientService.update(patient);

        // Assert
        assertNull(result);
        verify(patientRepository, times(1)).existsById(1);
        verify(patientRepository, times(0)).save(any());
    }

    // should return the newly created Patient. success.
    @Test
    void testCreate() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.create(patient);

        // Assert
        assertNotNull(result);
        assertEquals("john", result.getFirstName());
        verify(patientRepository, times(1)).save(patient);
    }

    // should return 'true' as the Patient has been successfully deleted.
    @Test
    void testDelete_success() {
        // Arrange
        when(patientRepository.existsById(1)).thenReturn(false);
        doNothing().when(patientRepository).deleteById(1); // call but dont perform

        // Act
        boolean result = patientService.delete(1);

        // Assert
        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(1);
        verify(patientRepository, times(1)).existsById(1);
    }

    // should return 'false' as the Patient could not be deleted (not found)
    @Test
    void testDelete_fail() {
        // Arrange
        when(patientRepository.existsById(1)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(1); // call but dont perform

        // Act
        boolean result = patientService.delete(1);

        // Assert
        assertFalse(result);
        verify(patientRepository, times(1)).deleteById(1);
        verify(patientRepository, times(1)).existsById(1);
    }
}
