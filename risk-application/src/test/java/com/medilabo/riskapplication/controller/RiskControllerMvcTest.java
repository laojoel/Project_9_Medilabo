package com.medilabo.riskapplication.controller;

import com.medilabo.riskapplication.service.RiskService;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskController.class)
@Import(RiskControllerMvcTest.TestConfig.class)
public class RiskControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RiskService riskService;

    // Custom configuration to mock the NoteService
    static class TestConfig {
        @Bean
        public RiskService riskService() {
            return Mockito.mock(RiskService.class);
        }
    }

    // should return the evaluation result as String
    @Test
    void testGetNotesPatientId() throws Exception {
        // Arrange
        int patientId = 1;
        String risk = "Low";
        when(riskService.riskEvaluation(patientId)).thenReturn(risk);

        // Act and Assert
        mockMvc.perform(get("/{patId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Low"));
    }


}
