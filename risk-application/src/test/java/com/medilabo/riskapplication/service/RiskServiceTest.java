package com.medilabo.riskapplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import com.medilabo.riskapplication.model.Note;
import com.medilabo.riskapplication.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RiskServiceTest {
    @Mock
    private PatientService patientService;
    @Mock
    private NoteService noteService;

    @InjectMocks
    private RiskService riskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // CASE 1 : Risk 'None'
    // patient's notes have no triggers
    @Test
    void testRiskEvaluation_None() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(30, "M");

        Note note1 = new Note();
        note1.setContent("all good");
        Note note2 = new Note();
        note2.setContent("everything is normal");
        List<Note> notes = Arrays.asList(note1, note2);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("None", result);
    }

    // CASE 2: Risk 'Borderline'
    // Patient is 30 years old or more and has 2 to 5 triggers in notes.
    @Test
    void testRiskEvaluation_BorderLine() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(38, "M");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps élevé");
        List<Note> notes = Arrays.asList(note1, note2);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("Borderline", result);
    }

    // CASE 3: Risk 'In Danger'
    // Scene 1: Male Patient, less than 30 years old and 3 triggers or more from notes
    @Test
    void testRiskEvaluation_DangerScene1() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(26, "M");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps bas");
        List<Note> notes = Arrays.asList(note1, note2);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("In danger", result);
    }

    // CASE 3: Risk 'In Danger'
    // Scene 2: Female Patient, less than 30 years old and 4 triggers from notes
    @Test
    void testRiskEvaluation_DangerScene2() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(26, "F");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps bas et vertige");
        List<Note> notes = Arrays.asList(note1, note2);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("In danger", result);
    }

    // CASE 3: Risk 'In Danger'
    // Scene 3: 30 years old and up, 6 or 7 triggers from notes
    @Test
    void testRiskEvaluation_DangerScene3() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(48, "M");

        Note note1 = new Note();
        note1.setContent("poids anormal et réaction cutané");
        Note note2 = new Note();
        note2.setContent("anticorps bas, vertige");
        Note note3 = new Note();
        note3.setContent("cholestérol modéré");
        List<Note> notes = Arrays.asList(note1, note2, note3);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("In danger", result);
    }

    // CASE 4: Risk 'Early onset'
    // Scene 1: Male Patient, less than 30 years old and 5 triggers or more from notes
    @Test
    void testRiskEvaluation_EarlyonsetScene1() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(26, "M");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps bas, hémoglobine A1C, fumeur");
        List<Note> notes = Arrays.asList(note1, note2);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("Early onset", result);
    }

    // CASE 4: Risk 'Early onset'
    // Scene 2: Female Patient, less than 30 years old and 7 triggers or more from notes
    @Test
    void testRiskEvaluation_EarlyonsetScene2() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(26, "F");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps bas, hémoglobine A1C, fumeuse");
        Note note3 = new Note();
        note3.setContent("microalbumine et vertiges");
        List<Note> notes = Arrays.asList(note1, note2, note3);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("Early onset", result);
    }

    // CASE 4: Risk 'Early onset'
    // Scene 3: 30 years old and up, 8 triggers or more from notes
    @Test
    void testRiskEvaluation_EarlyonsetScene3() {
        // Arrange
        Patient patient = new Patient();
        patient.setAgeGender(52, "F");

        Note note1 = new Note();
        note1.setContent("poids anormal");
        Note note2 = new Note();
        note2.setContent("anticorps bas, hémoglobine A1C, fumeuse");
        Note note3 = new Note();
        note3.setContent("microalbumine, vertiges et rechute");
        List<Note> notes = Arrays.asList(note1, note2, note3);

        when(patientService.getPatientId(1)).thenReturn(patient);
        when(noteService.getNotesPatId(1)).thenReturn(notes);

        // Act
        String result = riskService.riskEvaluation(1);

        // Assert
        assertEquals("Early onset", result);
    }
}
