package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class PatientService {
    private final RestTemplate      authRestTemplate;
    private final GatewayProperties routes;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Patient> getAllPatients() {
        log.info("fetch all patients");
        ResponseEntity<List<Patient>> responseEntity = authRestTemplate.exchange(
                routes.getAllPatientUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Patient>>() {}
        );
        return responseEntity.getBody();
    }
    public Patient getPatientId(long id) {
        log.info("fetch detail patients id " + id);
        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }
    public Patient modify(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientModificationUri()+"/"+patient.getId(),
                HttpMethod.POST,
                new HttpEntity<>(patient, headers),
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }

    public Patient create(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientCreationUri(),
                HttpMethod.POST,
                new HttpEntity<>(patient, headers),
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }

    public Patient delete(long id) {
        log.info("delete patients id " + id);
        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientDeletionUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }
}
