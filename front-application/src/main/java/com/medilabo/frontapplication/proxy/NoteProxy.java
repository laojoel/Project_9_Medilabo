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
import com.medilabo.frontapplication.model.Note;

@Component
public class NoteProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;
    private final HttpHeaders headers = new HttpHeaders() {{
        setContentType(MediaType.APPLICATION_JSON);
    }};

    public NoteProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Note> getNotesFromPatId(int patId) {
        return authRestTemplate.exchange(routes.getPatIdNotesUri()+"/"+patId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>() {}).getBody();
    }

    public Note get(String id) {
        return authRestTemplate.exchange(routes.getNoteUri()+"/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<Note>() {}).getBody();
    }

    public Note create(Note note) {
        return authRestTemplate.exchange(routes.getNoteCreationUri(), HttpMethod.POST, new HttpEntity<>(note, headers), new ParameterizedTypeReference<Note>() {}).getBody();
    }

    public Note modify(Note note) {
        return authRestTemplate.exchange(routes.getNoteModificationUrl(), HttpMethod.POST, new HttpEntity<>(note, headers), new ParameterizedTypeReference<Note>() {}).getBody();
    }

    public Boolean delete(String id) {
        return authRestTemplate.exchange(routes.getNoteDeletionUri() + "/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<Boolean>() {}).getBody();
    }
}
