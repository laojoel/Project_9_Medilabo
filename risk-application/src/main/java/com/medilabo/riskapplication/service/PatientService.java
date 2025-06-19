package com.medilabo.riskapplication.service;

import org.springframework.beans.factory.annotation.Qualifier;
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
    private final RestTemplate authRestTemplate;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes, RestTemplate restTemplate) {
        this.routes = routes;
        this.authRestTemplate = authRestTemplate;
    }

    public Patient getPatientId(long id) {
        log.info("fetch detail patients id " + id);
        ResponseEntity<Patient> responseEntity = authRestTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return responseEntity.getBody();
    }
}
