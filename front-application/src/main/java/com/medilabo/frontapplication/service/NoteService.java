package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.GatewayRoutes;
import com.medilabo.frontapplication.proxy.NoteRoutes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class NoteService {
    private final RestTemplate authRestTemplate;
    private final NoteRoutes routes;

    public NoteService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, NoteRoutes routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Note> getAllNotesPatId(String patId) {
        log.info("fetch note patId");
        ResponseEntity<List<Note>> responseEntity = authRestTemplate.exchange(
                routes.getAllNotesUri()+"/"+patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {}
        );
        System.out.println("NOTES AAA | Count = " + responseEntity.getBody().size());
        System.out.println("note content = " + responseEntity.getBody().get(0).getContent());
        return responseEntity.getBody();
    }

    public Note getNotePatId(String patId) {
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
}
