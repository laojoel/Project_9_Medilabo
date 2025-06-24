package com.medilabo.riskapplication.service;

import org.springframework.stereotype.Service;

import java.util.List;

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