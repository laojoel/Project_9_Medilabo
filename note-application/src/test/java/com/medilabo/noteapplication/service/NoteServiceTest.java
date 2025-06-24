package com.medilabo.noteapplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.repository.NoteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // return the requested note from note ID. success
    @Test
    void testGetById_Success() {
        // Arrange
        String noteId = "abc123";
        Note note = new Note();
        note.setId(noteId);
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        // Act
        Note result = noteService.getById(noteId);

        // Assert
        assertNotNull(result);
        assertEquals("abc123", result.getId());
        verify(noteRepository, times(1)).findById(noteId);
    }

    // should return null because the requested note ID could not be found
    @Test
    void testGetById_notFound() {
        // Arrange
        String noteId = "abc123";
        when(noteRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Note result = noteService.getById(noteId);

        // Assert
        assertNull(result);
        verify(noteRepository, times(1)).findById(noteId);
    }

    // return the newly created Note, success
    @Test
    void testCreate() {
        // Arrange
        Note note = new Note();
        note.setId("abc123");
        Note savedNode = new Note();
        savedNode.setId("abc123");
        when(noteRepository.save(note)).thenReturn(savedNode);

        // Act
        Note result = noteService.create(note);

        // Assert
        assertNotNull(result);
        assertEquals("abc123", result.getId());
        verify(noteRepository, times(1)).save(note);
    }

    // return the updated note, success.
    @Test
    void testModify_Success() {
        // Arrange
        String noteId = "abc123";

        Note note = new Note();
        note.setNote("message-original");
        note.setId(noteId);

        Note modifiedNote = new Note();
        modifiedNote.setId(noteId);
        modifiedNote.setNote("message-modified");

        when(noteRepository.existsById(noteId)).thenReturn(true);
        when(noteRepository.save(note)).thenReturn(modifiedNote);

        // Act
        Note result = noteService.update(note);

        // Assert
        assertNotNull(result);
        assertEquals("message-modified", result.getNote());
        verify(noteRepository, times(1)).save(note);
    }

    // should return null because note id to update doesn't exist
    @Test
    void testModify_NotFound() {
        // Arrange
        String noteId = "abc123";
        Note note = new Note();
        note.setId(noteId);

        when(noteRepository.existsById(noteId)).thenReturn(false);

        // Act
        Note result = noteService.update(note);

        // Assert
        assertNull(result);
        verify(noteRepository, times(1)).existsById(noteId);
    }

    // should return true when deletion succeeds
    @Test
    void testDelete_success() {
        // Arrange
        String noteId = "abc123";
        when(noteRepository.existsById(noteId)).thenReturn(false);

        // Act
        boolean result = noteService.delete(noteId);

        // Assert
        assertTrue(result);
        verify(noteRepository, times(1)).deleteById(noteId);
    }


    // should return false when deletion fails
    @Test
    void testDelete_NotFound() {
        // Arrange
        String noteId = "abc123";
        when(noteRepository.existsById(noteId)).thenReturn(true);

        // Act
        boolean result = noteService.delete(noteId);

        // Assert
        assertFalse(result);
        verify(noteRepository, times(1)).deleteById(noteId);
    }

}
