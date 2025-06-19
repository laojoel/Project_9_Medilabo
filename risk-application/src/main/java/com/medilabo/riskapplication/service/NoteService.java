package com.medilabo.riskapplication.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.medilabo.riskapplication.configuration.GatewayProperties;
import com.medilabo.riskapplication.model.Note;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoteService {

    private final GatewayProperties routes;
    private final RestTemplate      authRestTemplate;

    public NoteService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.routes = routes;
        this.authRestTemplate = authRestTemplate;
    }

    public List<Note> getNotesPatId(long patId) {
        log.info("fetch notes from patient id " + patId);
        ResponseEntity<List<Note>> responseEntity = authRestTemplate.exchange(
                routes.getPatIdNotesUri()+"/"+patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return responseEntity.getBody();
    }
}