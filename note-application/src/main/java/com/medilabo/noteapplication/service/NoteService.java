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
        return noteRepository.findAllByPatId(patId);
    }

    public Note getById(String id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElse(null);
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note update(Note note) {
        if (!noteRepository.existsById(note.getId())) {
            return null;
        }
        return noteRepository.save(note);
    }

    public boolean delete(String id) {
        noteRepository.deleteById(id);
        return !noteRepository.existsById(id);
    }
}
