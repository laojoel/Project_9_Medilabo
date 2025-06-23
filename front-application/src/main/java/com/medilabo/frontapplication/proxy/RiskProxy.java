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
public class RiskProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;
    private final HttpHeaders headers = new HttpHeaders() {{
        setContentType(MediaType.APPLICATION_JSON);
    }};

    public RiskProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public String getRiskForPatId(long patId) {
        return authRestTemplate.exchange(routes.getRiskUri()+"/"+patId, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();
    }

}
