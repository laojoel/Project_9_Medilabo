package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import com.medilabo.frontapplication.service.RiskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private RiskService riskService;

    private MockHttpSession getSessionWithToken() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("token", "mock-token"); // Mock token to pass the TokenInterceptor checkpoint
        return session;
    }

    @Test
    void testGetCreateNote_success() throws Exception {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");
        when(patientService.getPatientId(patientId)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(get("/notes/create")
                        .param("patId", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("noteCreate"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testGetCreateNote_InvalidPatient() throws Exception {
        // Arrange
        long patientId = 1;
        when(patientService.getPatientId(patientId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/notes/create")
                        .param("patId", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("error", "Patient could not be found"));
    }

    @Test
    void testPostCreateNote_ValidationErrors() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/notes/create")
                        .param("content", "")
                        .flashAttr("note", new Note())
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("noteCreate"))
                .andExpect(model().attributeHasFieldErrors("note", "content"));
    }

    @Test
    void testPostCreateNote_Success() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId("abc123");
        note.setPatId(1);
        note.setPatient("john smith");
        note.setContent("message-A");

        Patient patient = new Patient();
        patient.setIdAndFullName(1, "john", "smith");

        when(noteService.create(any(Note.class))).thenReturn(note);
        when(patientService.getPatientId(1)).thenReturn(patient);
        when(riskService.getRiskLevelForPatientId(1)).thenReturn("Low");

        // Act & Assert
        mockMvc.perform(post("/notes/create")
                        .flashAttr("note", note)
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("patient", "notes", "risk"))
                .andExpect(model().attribute("risk", "Low"));
    }

    @Test
    void testGetUpdateNote() throws Exception {
        // Arrange
        String noteId = "abc123";
        Note note = new Note();
        note.setId(noteId);

        when(noteService.getNoteId(noteId)).thenReturn(note);

        // Act & Assert
        mockMvc.perform(get("/notes/modify")
                        .param("id", noteId)
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("noteUpdate"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testPostUpdateNote_ValidationErrors() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/notes/modify")
                        .flashAttr("note", new Note())
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("noteUpdate"))
                .andExpect(model().attributeHasErrors("note"));
    }

    @Test
    void testPostUpdateNote_Success() throws Exception {
        // Arrange
        Note note = new Note();
        note.setId("abc123");
        note.setPatId(1);
        note.setPatient("john smith");
        note.setContent("message-A");

        Patient patient = new Patient();
        patient.setId(1);

        when(noteService.modify(any(Note.class))).thenReturn(note);
        when(patientService.getPatientId(1)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/notes/modify")
                        .flashAttr("note", note)
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("notes", "patient"));
    }

    @Test
    void testDeleteNote_Success() throws Exception {
        // Arrange
        String noteId = "abc123";
        long patientId = 1;

        Patient patient = new Patient();
        patient.setId(1);

        when(noteService.delete(noteId)).thenReturn(true);
        when(patientService.getPatientId(1)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(get("/notes/delete")
                        .param("id", noteId)
                        .param("patId", String.valueOf(patientId))
                        .session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("patientView"))
                .andExpect(model().attributeExists("notes", "patient"))
                .andExpect(model().attribute("message", "Note successfully deleted"));
    }
}
