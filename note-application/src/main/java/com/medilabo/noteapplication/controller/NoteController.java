package com.medilabo.noteapplication.controller;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Note> noteForPatient(@PathVariable("id") String patId) {
        log.info("get notes for patient ID {}", patId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteService.getById(patId));
    }
}
