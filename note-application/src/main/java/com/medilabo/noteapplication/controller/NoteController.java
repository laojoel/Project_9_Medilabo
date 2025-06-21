package com.medilabo.noteapplication.controller;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Note> noteId(@PathVariable("id") String id) {
        Note note = noteService.getById(id);
        if (note == null) {
            log.error("View Note ID: " + id + " | Failed for reason: Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("View note ID " + id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(note);
    }

    @GetMapping("patId/{patId}")
    public ResponseEntity<List<Note>> notesPatient(@PathVariable("patId") int patId) {
        log.info("get notes for patient ID {}", patId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteService.getAllByPatId(patId));
    }

    @PostMapping("create")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        log.info("Creating Note for Patient ID: " + note.getPatId() + " (" + note.getPatient() +")");
        Note createdNote = noteService.create(note);
        if (createdNote == null) {return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();}
        return ResponseEntity.status(HttpStatus.OK).body(createdNote);
    }

    @PostMapping("modify")
    public ResponseEntity<Note> update(@RequestBody Note note) {
        Note updatedNote = noteService.update(note);
        if (updatedNote == null) {
            log.error("Update Note ID: " + note.getId() + " | Failed for reason: Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
        else if (!updatedNote.getNote().equals(note.getNote())) {
            log.error("Update Note ID: " + note.getId() + " | Failed At database level");
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();}

        log.info("Update Note ID: " + note.getId());
        return ResponseEntity.status(HttpStatus.OK).body(updatedNote);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Note> delete(@PathVariable("id") String id) {
        if(noteService.delete(id)) {return ResponseEntity.status(HttpStatus.OK).build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

    }


}
