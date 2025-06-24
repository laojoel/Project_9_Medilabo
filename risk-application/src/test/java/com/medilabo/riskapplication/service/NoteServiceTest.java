package com.medilabo.riskapplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.medilabo.riskapplication.proxy.NoteProxy;
import com.medilabo.riskapplication.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class NoteServiceTest {

    @Mock
    private NoteProxy noteProxy;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // should return Note from Patient ID
    @Test
    void testGetNotesPatId() {
        // Arrange
        long patientId = 1;
        Note note1 = new Note();
        note1.setContent("message-A");
        Note note2 = new Note();
        note2.setContent("message-B");

        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);

        when(noteProxy.getNotesFromPatId((int)patientId)).thenReturn(notes);

        // Act
        List<Note> result = noteService.getNotesPatId(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("message-A", result.getFirst().getContent());
        assertEquals("message-B", result.get(1).getContent());
        verify(noteProxy, times(1)).getNotesFromPatId((int)patientId);
    }
}
