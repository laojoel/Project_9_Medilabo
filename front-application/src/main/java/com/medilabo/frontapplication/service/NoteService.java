package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class NoteService {
    private final RestTemplate      authRestTemplate;
    private final GatewayProperties routes;

    public NoteService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Note> getAllNotesPatId(long patId) {
        log.info("fetch note patId");
        ResponseEntity<List<Note>> responseEntity = authRestTemplate.exchange(
                routes.getAllNotesUri()+"/"+(int)patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {}
        );
        return responseEntity.getBody();
    }

    public Note getNoteId(String patId) {
        log.info("fetch note patId");
        ResponseEntity<Note> responseEntity = authRestTemplate.exchange(
                routes.getNoteViewUri()+"/"+patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Note>() {}
        );
        return responseEntity.getBody();
    }

    public Note create(Note note) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Note> responseEntity = authRestTemplate.exchange(
                routes.getNoteCreateUri(),
                HttpMethod.POST,
                new HttpEntity<>(note, headers),
                new ParameterizedTypeReference<Note>() {}
        );
        return responseEntity.getBody();
    }

    public boolean update(Note note) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            authRestTemplate.exchange(
                    routes.getNoteUpdateUri(),
                    HttpMethod.POST,
                    new HttpEntity<>(note, headers),
                    new ParameterizedTypeReference<Void>() {}
            );
            return true;
        }
        catch (HttpClientErrorException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            authRestTemplate.exchange(
                    routes.getNoteDeleteUri() + "/" + id,
                    HttpMethod.DELETE,
                    null,
                    Void.class);
            return true;
        }
        catch (HttpClientErrorException e) {
            return false;
        }
    }
}
