package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.GatewayRoutes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class NoteService {
    private final RestTemplate authRestTemplate;
    private final GatewayRoutes routes;

    public NoteService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayRoutes routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }
    public Note getNotePatId(String patId) {
        log.info("fetch note patId");
        ResponseEntity<Note> responseEntity = authRestTemplate.exchange(
                routes.getPatientViewUri()+"/"+patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Note>() {}
        );
        return responseEntity.getBody();
    }
}
