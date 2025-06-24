package com.medilabo.noteapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@Import(NoteControllerTest.TestConfig.class) // Import custom test configuration
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoteService noteService; // Mocked service

    // Custom configuration to mock the NoteService
    static class TestConfig {
        @Bean
        public NoteService noteService() {
            return Mockito.mock(NoteService.class);
        }
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(noteService); // Reset the mock before each test
    }

    // should return the Note associated to the given note ID
    @Test
    void testGetNoteById_Success() throws Exception {
        Note note = new Note();
        note.setId("abc123");
        note.setNote("message-A");

        when(noteService.getById("abc123")).thenReturn(note);

        mockMvc.perform(get("/{id}", "abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.note").value("message-A"));
    }

    // should return null as no Note match the given note ID
    @Test
    void testGetNoteById_NotFound() throws Exception {
        when(noteService.getById("abc123")).thenReturn(null);

        mockMvc.perform(get("/{id}", "abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    // should return all notes from the given Patient ID
    @Test
    void testGetNotesByPatientId() throws Exception {
        // Arrange
        Note note1 = new Note();
        note1.setId("abc123");
        note1.setNote("message-A");

        Note note2 = new Note();
        note2.setId("xyz789");
        note2.setNote("message-B");

        List<Note> notes = Arrays.asList(note1, note2);

        when(noteService.getAllByPatId(1)).thenReturn(notes);

        mockMvc.perform(get("/patId/{patId}", 1))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("abc123"))
                .andExpect(jsonPath("$[0].note").value("message-A"))
                .andExpect(jsonPath("$[1].id").value("xyz789"))
                .andExpect(jsonPath("$[1].note").value("message-B"));
    }

    // should successfully return the newly created Note
    @Test
    void testCreateNote() throws Exception {
        Note note = new Note();
        note.setId("abc123");
        note.setNote("message-A");

        when(noteService.create(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.note").value("message-A"));
    }

    // should successfully return the updated Note
    @Test
    void testUpdateNote() throws Exception {
        Note note = new Note();
        note.setId("abc");
        note.setNote("message-A");

        when(noteService.update(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/modify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc"))
                .andExpect(jsonPath("$.note").value("message-A"));
    }

    // should return 'true' as the given Note ID has successfully been deleted.
    @Test
    void testDeleteNote() throws Exception {
        when(noteService.delete("abc123")).thenReturn(true);

        mockMvc.perform(delete("/delete/{id}", "abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // should return 'false' as the given Note ID could not be found
    @Test
    void testDeleteNote_Failure() throws Exception {
        when(noteService.delete("abc123")).thenReturn(false);

        mockMvc.perform(delete("/delete/{id}", "abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
