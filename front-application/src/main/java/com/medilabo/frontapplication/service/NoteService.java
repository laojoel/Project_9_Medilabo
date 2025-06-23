package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.proxy.NoteProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoteService {
    private final NoteProxy noteProxy;

    public NoteService(NoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    public List<Note> getAllNotesPatId(long patId) {
        return noteProxy.getNotesFromPatId((int)patId);
    }

    public Note getNoteId(String id) {
        return noteProxy.get(id);
    }

    public Note create(Note note) {
        return noteProxy.create(note);
    }

    public Note modify(Note note) {
        return noteProxy.modify(note);
    }

    public boolean delete(String id) {
        Boolean result = noteProxy.delete(id);
        if (result == null) {return false;}
        return result;
    }
}
