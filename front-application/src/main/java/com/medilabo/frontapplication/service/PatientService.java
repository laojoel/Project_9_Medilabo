package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.GatewayRoutes;
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
    private final RestTemplate authRestTemplate;
    private final GatewayRoutes routes;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayRoutes routes) {
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
        log.info("fetch detail patients id " + id + " | " + routes.getPatientViewUri()+"/"+id);
        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientViewUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }
    public Patient updatePatient(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientUpdateUri()+"/"+patient.getId(),
                HttpMethod.POST,
                new HttpEntity<>(patient, headers),
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }

    public Patient createPatient(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientCreateUri(),
                HttpMethod.POST,
                new HttpEntity<>(patient, headers),
                new ParameterizedTypeReference<Patient>() {}
        );
        return responseEntity.getBody();
    }
}
