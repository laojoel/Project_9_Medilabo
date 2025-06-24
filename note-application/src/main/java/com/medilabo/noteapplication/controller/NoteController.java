package com.medilabo.noteapplication.controller;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            log.error("Note ID: " + id + " Not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    @GetMapping("patId/{patId}")
    public ResponseEntity<List<Note>> notesPatient(@PathVariable("patId") int patId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteService.getAllByPatId(patId));
    }

    @PostMapping("create")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        Note createdNote = noteService.create(note);
        if (createdNote == null) {
            log.error("Note could not be created");
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdNote);
    }

    @PostMapping("modify")
    public ResponseEntity<Note> update(@RequestBody Note note) {
        Note updatedNote = noteService.update(note);
        if (updatedNote == null) {
            log.error("Note ID: " + note.getId() + " could not be updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedNote);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        Boolean result = noteService.delete(id);
        if (!result) {log.error("Note ID " + id + " could not be deleted");}
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
