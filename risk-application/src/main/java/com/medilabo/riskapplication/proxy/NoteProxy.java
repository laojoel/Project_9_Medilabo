package com.medilabo.riskapplication.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.medilabo.riskapplication.configuration.GatewayProperties;
import com.medilabo.riskapplication.model.Note;

@Component
public class NoteProxy {
    private final RestTemplate authRestTemplate;
    private final GatewayProperties routes;

    public NoteProxy(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayProperties routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Note> getNotesFromPatId(int patId) {
        return authRestTemplate.exchange(routes.getPatIdNotesUri()+"/"+patId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>() {}).getBody();
    }
}
