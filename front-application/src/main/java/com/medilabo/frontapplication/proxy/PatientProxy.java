package com.medilabo.frontapplication.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Patient;

@Component
public class PatientProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;
    private final HttpHeaders headers = new HttpHeaders() {{
        setContentType(MediaType.APPLICATION_JSON);
    }};

    public PatientProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Patient> getAll() {
        return authRestTemplate.exchange(routes.getAllPatientUri(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Patient>>() {}).getBody();
    }

    public Patient get(long id) {
        return authRestTemplate.exchange(routes.getPatientUri()+"/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<Patient>() {}).getBody();
    }

    public Patient create(Patient patient) {
        return authRestTemplate.exchange(routes.getPatientCreationUri(), HttpMethod.POST, new HttpEntity<>(patient, headers), new ParameterizedTypeReference<Patient>() {}).getBody();
    }

    public Patient modify(Patient patient) {
        return authRestTemplate.exchange(routes.getPatientModificationUri(), HttpMethod.POST, new HttpEntity<>(patient, headers), new ParameterizedTypeReference<Patient>() {}).getBody();
    }

    public Boolean delete(long id) {
        return authRestTemplate.exchange(routes.getPatientDeletionUri() + "/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<Boolean>() {}).getBody();
    }
}
