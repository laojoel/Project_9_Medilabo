package com.medilabo.frontapplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.medilabo.frontapplication.proxy.RiskProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RiskServiceTest {

    @Mock
    private RiskProxy riskProxy;

    @InjectMocks
    private RiskService riskService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRiskLevelForPatientId() {
        // Given
        long patientId = 1;
        String risk = "Low";
        when(riskProxy.getRiskForPatId(patientId)).thenReturn(risk);

        // When
        String result = riskService.getRiskLevelForPatientId(patientId);

        // Then
        assertNotNull(result);
        assertEquals("Low", result);
        verify(riskProxy, times(1)).getRiskForPatId(patientId);
    }

    @Test
    void testGetRiskLevelForPatientId_Unavailable() {
        // Given
        long patientId = 1;
        when(riskProxy.getRiskForPatId(patientId)).thenReturn(null);

        // When
        String result = riskService.getRiskLevelForPatientId(patientId);

        // Then
        assertNull(result);
        verify(riskProxy, times(1)).getRiskForPatId(patientId);
    }
}
