package com.medilabo.riskapplication.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.medilabo.riskapplication.configuration.GatewayProperties;
import com.medilabo.riskapplication.model.Patient;

@Component
public class PatientProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;

    public PatientProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public Patient get(long id) {
        return authRestTemplate.exchange(routes.getPatientUri()+"/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<Patient>() {}).getBody();
    }
}
