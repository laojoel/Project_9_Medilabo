package com.medilabo.riskapplication.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.riskapplication.configuration.GatewayProperties;
import com.medilabo.riskapplication.model.Patient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientService {

    private final GatewayProperties routes;
    private final RestTemplate restTemplate;

    public PatientService(GatewayProperties routes, RestTemplate restTemplate) {
        this.routes = routes;
        this.restTemplate = restTemplate;
    }

    public Patient getPatientId(long id) {
        log.info("fetch detail patients id " + id);
        ResponseEntity<Patient> responseEntity = restTemplate.exchange(
                routes.getPatientViewUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return responseEntity.getBody();
    }
}
