package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.proxy.NoteProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteProxy noteProxy;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotesPatId() {
        // Arrange
        long patId = 1;
        Note note1 = new Note();
        note1.setPatIdAndContent(1, "message-A");
        Note note2 = new Note();
        note2.setPatIdAndContent(2, "message-B");
        List<Note> notes = Arrays.asList(note1, note2);
        when(noteProxy.getNotesFromPatId((int) patId)).thenReturn(notes);

        // Act
        List<Note> result = noteService.getAllNotesPatId(patId);

        // Assert
        assertNotNull(result);
        assertEquals("message-A", result.getFirst().getContent());
        assertEquals("message-B", result.get(1).getContent());
        verify(noteProxy, times(1)).getNotesFromPatId((int) patId);
    }

    @Test
    void testGetNoteId() {
        // Arrange
        String noteId = "abc123";
        Note note = new Note();
        note.setId(noteId);
        when(noteProxy.get(noteId)).thenReturn(note);

        // Act
        Note result = noteService.getNoteId(noteId);

        // Assert
        assertNotNull(result);
        assertEquals("abc123", result.getId());
        verify(noteProxy, times(1)).get(noteId);
    }

    @Test
    void testCreate() {
        // Arrange
        Note note = new Note();
        note.setPatIdAndContent(1,"message-A");
        when(noteProxy.create(note)).thenReturn(note);

        // Act
        Note result = noteService.create(note);

        // Assert
        assertNotNull(result);
        assertEquals("message-A", result.getContent());
        verify(noteProxy, times(1)).create(note);
    }

    @Test
    void testModify() {
        // Arrange
        Note note = new Note();
        note.setPatIdAndContent(1, "original");
        Note modifiedNote = new Note();
        modifiedNote.setPatIdAndContent(1, "modified");
        when(noteProxy.modify(note)).thenReturn(modifiedNote);

        // Act
        Note result = noteService.modify(note);

        // Assert
        assertNotNull(result);
        assertEquals("modified", result.getContent());
        verify(noteProxy, times(1)).modify(note);
    }

    @Test
    void testDelete() {
        // Arrange
        String noteId = "abc123";
        when(noteProxy.delete(noteId)).thenReturn(true);

        // Act
        boolean result = noteService.delete(noteId);

        // Assert
        assertTrue(result);
        verify(noteProxy, times(1)).delete(noteId);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        String noteId = "abc123";
        when(noteProxy.delete(noteId)).thenReturn(null);

        // Act
        boolean result = noteService.delete(noteId);

        // Assert
        assertFalse(result);
        verify(noteProxy, times(1)).delete(noteId);
    }
}
