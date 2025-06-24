package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // return all patients from patient application
    @Test
    void testGetAllPatients() {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setIdAndFullName(1, "john", "smith");
        Patient patient2 = new Patient();
        patient2.setIdAndFullName(2, "jane", "doe");
        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientProxy.getAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(2, patients.size());
        verify(patientProxy, times(1)).getAll();
        assertEquals("john", result.getFirst().getFirstName());
        assertEquals("jane", result.get(1).getFirstName());
    }

    // successfully returns a Patient object from ID
    @Test
    void testGetPatientId() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientProxy.get(1)).thenReturn(patient);

        // Act
        Patient result = patientService.getPatientId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("john", result.getFirstName());
        verify(patientProxy, times(1)).get(1);
    }

    // successfully return the modified Patient
    @Test
    void testModify() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        Patient modifiedPatient = new Patient();
        modifiedPatient.setIdAndFullName(1, "edit", "smith");
        when(patientProxy.modify(patient)).thenReturn(modifiedPatient);

        // Act
        Patient result = patientService.modify(patient);

        // Assert
        assertNotNull(result);
        assertEquals("edit", result.getFirstName());
        verify(patientProxy, times(1)).modify(patient);
    }

    // successfully return the created Patient
    @Test
    void testCreate() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientProxy.create(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.create(patient);

        // Assert
        assertNotNull(result);
        assertEquals("john", result.getFirstName());
        verify(patientProxy, times(1)).create(patient);
    }

    // Successfully return boolean True as the deletion was completed
    @Test
    void testDeleteSuccess() {
        // Arrange
        when(patientProxy.delete(1)).thenReturn(true);

        // Act
        boolean result = patientService.delete(1);

        // Assert
        assertTrue(result);
        verify(patientProxy, times(1)).delete(1);
    }

    // should return False as the deletion failed for this patient ID (not found)
    @Test
    void testDeleteNotFound() {
        // Arrange
        when(patientProxy.delete(1)).thenReturn(null);

        // Act
        boolean result = patientService.delete(1);

        // Assert
        assertFalse(result);
        verify(patientProxy, times(1)).delete(1L);
    }
}
