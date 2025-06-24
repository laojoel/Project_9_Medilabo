package com.medilabo.patientapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patientapplication.model.Patient;
import com.medilabo.patientapplication.service.PatientService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@Import(PatientControllerMvcTest.TestConfig.class)
public class PatientControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    // Custom configuration to mock the NoteService
    static class TestConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }

    // should return a List of all Patients
    @Test
    void testAllPatients() throws Exception {
        Patient patient1 = new Patient();
        patient1.setIdAndFullName(1, "john", "smith");
        Patient patient2 = new Patient();
        patient2.setIdAndFullName(2, "jane", "doe");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);

        when(patientService.getAll()).thenReturn(patients);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("john"))
                .andExpect(jsonPath("$[1].firstName").value("jane"));
    }

    // should return a Patient given his ID
    @Test
    void testPatientById() throws Exception {
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");

        when(patientService.getById(anyInt())).thenReturn(patient);

        mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("john"));
    }

    // should return the newly created Patient
    @Test
    void testCreatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");

        when(patientService.create(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("john"));
    }

    // should return the updated patient
    @Test
    void testUpdatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        Patient modifiedPatient = new Patient();
        modifiedPatient.setIdAndFullName(1, "edit", "smith");

        when(patientService.update(any(Patient.class))).thenReturn(modifiedPatient);

        mockMvc.perform(post("/modify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("edit"));
    }

    // should return 'true' as the Patient has been successfully deleted
    @Test
    void testDeletePatient() throws Exception {
        when(patientService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }
}
