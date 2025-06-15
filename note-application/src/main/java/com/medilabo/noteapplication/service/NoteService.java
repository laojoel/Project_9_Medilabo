package com.medilabo.noteapplication.service;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllByPatId(int patId) {
        List<Note> notes = noteRepository.findAllByPatId(patId);
        return notes;
    }

    public Note getById(String id) {
        Optional<Note> patients = noteRepository.findById(id);
        if (patients.isEmpty()) {
            log.error("note id " + id + " not found");
            return null;
        }
        return patients.get();
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note update(Note note) {
        if (!noteRepository.existsById(note.getId())) {
            log.error("note with id " + note.getId() + " not found for update");
            return null;
        }
        return noteRepository.save(note);
    }
}
