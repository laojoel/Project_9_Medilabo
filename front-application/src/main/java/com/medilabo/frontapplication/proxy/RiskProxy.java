package com.medilabo.frontapplication.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.medilabo.frontapplication.configuration.GatewayProperties;

@Component
public class RiskProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;

    public RiskProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public String getRiskForPatId(long patId) {
        return authRestTemplate.exchange(routes.getRiskUri()+"/"+patId, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();
    }

}
