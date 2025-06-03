package com.medilabo.noteapplication.service;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note getById(String patId) {
        Optional<Note> patients = noteRepository.findById(patId);
        if (patients.isEmpty()) {
            log.error("Patient with patId " + patId + " not found");
            return null;
        }
        return patients.get();
    }
}
