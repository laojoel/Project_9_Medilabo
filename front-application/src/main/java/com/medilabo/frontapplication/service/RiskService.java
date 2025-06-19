package com.medilabo.frontapplication.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Note;

@Service
public class RiskService {

    private final RestTemplate      authRestTemplate;
    private final GatewayProperties routes;

    public RiskService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public String getRiskLevel(long patId) {
        ResponseEntity<String> responseEntity = authRestTemplate.exchange(
                routes.getRiskUri()+"/"+(int)patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        if (!(responseEntity.getStatusCode() == HttpStatus.OK)) {
            return "?";
        }
        return responseEntity.getBody();

    }


}
