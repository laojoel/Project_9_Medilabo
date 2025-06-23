package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import com.medilabo.frontapplication.service.RiskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private NoteService noteService;

    @MockBean
    private RiskService riskService;

    private MockHttpSession getSessionWithToken() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("token", "mock-token"); // Mock token to pass the TokenInterceptor checkpoint
        return session;
    }

    @Test
    void testGetPatientView_success() throws Exception {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setIdAndFullName(1,"john", "smith");

        when(patientService.getPatientId(patientId)).thenReturn(patient);
        when(riskService.getRiskLevelForPatientId(patientId)).thenReturn("Low");
        when(noteService.getAllNotesPatId(patientId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/patients/view")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("patient", "risk", "notes"))
                .andExpect(model().attribute("risk", "Low"));
    }

    // should send back the user to patient view since the patient view id is invalid.
    @Test
    void testGetPatientView_InvalidPatientId() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientService.getPatientId(patientId)).thenReturn(null);
        when(patientService.getAllPatients()).thenReturn(Arrays.asList(new Patient(), new Patient()));

        // Act & Assert
        mockMvc.perform(get("/patients/view")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("error", "Patient could not be found"));
    }

    @Test
    void testGetModifyPatient_Success() throws Exception {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientService.getPatientId(patientId)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(get("/patients/modify")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientUpdate"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void testGetModifyPatient_InvalidId() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientService.getPatientId(patientId)).thenReturn(null);
        when(patientService.getAllPatients()).thenReturn(Arrays.asList(new Patient(), new Patient()));

        // Act & Assert
        mockMvc.perform(get("/patients/modify")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attribute("error", "Patient could not be found"));
    }

    // Post Modify Client should fail because if form input validation
    // the user will be sent back
    @Test
    void testPostModifyPatient_ValidationErrors() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/patients/modify")
                        .param("firstName", "") // empty firstName to trigger validation error
                        .flashAttr("patient", new Patient())
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientUpdate"))
                .andExpect(model().attributeHasErrors("patient"));
    }

    @Test
    void testPostModifyPatient_Success() throws Exception {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1);

        when(patientService.modify(patient)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/patients/modify")
                        .flashAttr("patient", patient)
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("message", "Patient successfully updated"));
    }

    @Test
    void testGetCreatePatient() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/patients/create")
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientCreate"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void testPostCreatePatient_ValidationErrors() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/patients/create")
                        .param("firstName", "") // empty firstName to trigger validation error
                        .flashAttr("patient", new Patient())
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientCreate"))
                .andExpect(model().attributeHasErrors("patient"));
    }

    @Test
    void testPostCreatePatient_Success() throws Exception {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1);

        when(patientService.create(patient)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/patients/create")
                        .flashAttr("patient", patient)
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("message", "Patient successfully created"));
    }

    @Test
    void testPatientDelete_Success() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientService.delete(patientId)).thenReturn(true);
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/patients/delete")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attribute("message", "Patient successfully deleted"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testPatientDelete_Failure() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientService.delete(patientId)).thenReturn(false);
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/patients/delete")
                        .param("id", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attribute("error", "Patient could not be deleted"))
                .andExpect(model().attributeExists("patients"));
    }
}
