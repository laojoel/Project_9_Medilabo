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
import com.medilabo.riskapplication.proxy.NoteProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoteService {

    private final NoteProxy noteProxy;
    public NoteService(NoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    public List<Note> getNotesPatId(long patId) {
        return noteProxy.getNotesFromPatId((int)patId);
    }
}