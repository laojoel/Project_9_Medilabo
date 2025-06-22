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
import com.medilabo.frontapplication.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    private PatientService patientService;
    private final static String PATIENT_URI = "http://gateway_uri_example.com/patients";

    @Mock
    private RestTemplate authRestTemplate;

    @Mock
    private GatewayProperties routes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(authRestTemplate, routes);
    }

    @Test
    void getAllPatients_Test() {
        // Arrange
        when(routes.getAllPatientUri()).thenReturn(PATIENT_URI);

        Patient patient0 = new Patient();
        patient0.setFirstName("Frank");
        Patient patient1 = new Patient();
        patient1.setFirstName("Sophie");
        List<Patient> patients =  Arrays.asList(patient0, patient1);
        ResponseEntity<List<Patient>> responseEntity = new ResponseEntity<>(patients, HttpStatus.OK);

        when(authRestTemplate.exchange(
                eq(PATIENT_URI + "/all"),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Patient>>>any()
        )).thenReturn(responseEntity);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(authRestTemplate, times(1)).exchange(
                eq(PATIENT_URI + "/all"),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Patient>>>any()
        );
        assertEquals("Frank", result.getFirst().getFirstName());
        assertEquals("Sophie", result.get(1).getFirstName());
    }

    @Test
    void getPatientId_Test() {
        // Arrange
        when(routes.getAllPatientUri()).thenReturn(PATIENT_URI);

        Patient patient0 = new Patient();
        patient0.setFirstName("Frank");
        Patient patient1 = new Patient();
        patient1.setFirstName("Sophie");
        List<Patient> patients =  Arrays.asList(patient0, patient1);
        ResponseEntity<List<Patient>> responseEntity = new ResponseEntity<>(patients, HttpStatus.OK);

        when(authRestTemplate.exchange(
                eq(PATIENT_URI + "/all"),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Patient>>>any()
        )).thenReturn(responseEntity);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(authRestTemplate, times(1)).exchange(
                eq(PATIENT_URI + "/all"),
                eq(HttpMethod.GET),
                isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Patient>>>any()
        );
        assertEquals("Frank", result.getFirst().getFirstName());
        assertEquals("Sophie", result.get(1).getFirstName());
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