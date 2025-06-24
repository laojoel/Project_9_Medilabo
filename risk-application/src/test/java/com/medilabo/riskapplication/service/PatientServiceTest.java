package com.medilabo.riskapplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.medilabo.riskapplication.model.Patient;
import com.medilabo.riskapplication.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PatientServiceTest {

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // should return Patient from Patient ID
    @Test
    void testGetPatientId() {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("john");

        when(patientProxy.get(patientId)).thenReturn(patient);

        // Act
        Patient result = patientService.getPatientId(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(patientId, result.getId());
        assertEquals("john", result.getFirstName());
        verify(patientProxy, times(1)).get(patientId);
    }
}
