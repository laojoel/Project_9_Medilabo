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
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/{patId}")
    public ResponseEntity<List<Note>> notesPatient(@PathVariable("patId") int patId) {
        log.info("get notes for patient ID {}", patId);
        System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteService.getAllByPatId(patId));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Note> noteId(@PathVariable("id") String id) {
        log.info("get note ID {}", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        log.info("Creating Note for Patient ID: " + note.getPatId() + " (" + note.getPatient() +")");
        Note createdNote = noteService.create(note);
        return ResponseEntity.status(HttpStatus.OK).body(createdNote);
    }

    @PostMapping("/update")
    public ResponseEntity<Note> update(@RequestBody Note note) {
        log.info("Create Note from Patient ID: " + note.getPatId() + " (" + note.getPatient() +")");
        Note updatedNote = noteService.update(note);
        return ResponseEntity.status(HttpStatus.OK).body(updatedNote);
    }


}
