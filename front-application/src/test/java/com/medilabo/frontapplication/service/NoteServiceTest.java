package com.medilabo.frontapplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteServiceTest {

    private NoteService noteService;
    private final static String NOTE_URI = "http://gateway_uri_example.com/notes";

    @Mock
    private RestTemplate authRestTemplate;

    @Mock
    private GatewayProperties routes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        noteService = new NoteService(authRestTemplate, routes);
    }

    @Test
    void getAllNotesPatId_shouldReturnNotes() {
        // Arrange
        long patId = 8L;
        when(routes.getPatIdNotesUri()).thenReturn(NOTE_URI);

        Note note0 = new Note();
        note0.setContent("content 0");
        Note note1 = new Note();
        note1.setContent("content 1");
        List<Note> notes =  Arrays.asList(note0, note1);
        ResponseEntity<List<Note>> responseEntity = new ResponseEntity<>(notes, HttpStatus.OK);

        when(authRestTemplate.exchange(
                eq(NOTE_URI + "/" + (int) patId),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Note>>>any()
        )).thenReturn(responseEntity);

        // Act
        List<Note> result = noteService.getAllNotesPatId(patId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(authRestTemplate, times(1)).exchange(
                eq(NOTE_URI + "/" + (int) patId),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Note>>>any()
        );
        assertEquals("content 0", result.getFirst().getContent());
        assertEquals("content 1", result.get(1).getContent());
    }

    @Test
    void getNoteId_shouldReturnOneNote() {
        // Arrange
        String noteId = "abc123";
        when(routes.getNoteUri()).thenReturn(NOTE_URI);

        Note note = new Note();
        note.setContent("test content");
        ResponseEntity<Note> responseEntity = new ResponseEntity<>(note, HttpStatus.OK);

        when(authRestTemplate.exchange(
                eq(NOTE_URI + "/" + noteId),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<Note>>any()
        )).thenReturn(responseEntity);

        // Act
        Note result = noteService.getNoteId(noteId);

        // Assert
        assertNotNull(result);
        verify(authRestTemplate, times(1)).exchange(
                eq(NOTE_URI + "/" + noteId),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<Note>>any()
        );
        assertEquals("test content", result.getContent());
    }

    @Test
    void create_shouldReturnCreatedNote() {
        // Arrange
        when(routes.getNoteCreationUri()).thenReturn(NOTE_URI);

        Note note = new Note();
        note.setContent("test content");
        ResponseEntity<Note> responseEntity = new ResponseEntity<>(note, HttpStatus.CREATED);

        when(authRestTemplate.exchange(
                eq(NOTE_URI),
                eq(HttpMethod.POST),
                ArgumentMatchers.<HttpEntity<Note>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<Note>>any()
        )).thenReturn(responseEntity);

        // Act
        Note result = noteService.create(note);

        // Assert
        assertNotNull(result);
        verify(authRestTemplate, times(1)).exchange(
                eq(NOTE_URI),
                eq(HttpMethod.POST),
                ArgumentMatchers.<HttpEntity<Note>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<Note>>any()
        );
        assertEquals("test content", result.getContent());
    }



}
