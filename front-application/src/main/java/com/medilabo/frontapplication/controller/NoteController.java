package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.medilabo.frontapplication.constant.ExecutionConstant.GENERIC_DELETION_ERROR;
import static com.medilabo.frontapplication.constant.ExecutionConstant.GENERIC_ERROR;

@Slf4j
@RequestMapping("/notes")
@Controller
public class NoteController {
    private final NoteService noteService;
    private final PatientService patientService;

    public NoteController(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    @GetMapping("/create")
    public String create(@RequestParam("patId") Long patId, Model model) {
        Patient patient = patientService.getPatientId(patId);
        if (patient==null) {
            log.error("create note for null patient, redirect to list");
            return "patients";
        }

        Note note = new Note();
        note.setPatId(patId);
        note.setPatient(patient.getFullName());
        note.setContent("");

        model.addAttribute("note", note);
        return "noteCreate";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("note") Note note, BindingResult result,
                                Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.warn("note creation form has incorrect entries");
            return "noteCreate";
        }
        noteService.create(note);
        long patId = note.getPatId();
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }

    @GetMapping("/modify")
    public String update(@RequestParam("id") String id, Model model) {
        log.info("Display note ID " + id);
        model.addAttribute("note", noteService.getNoteId(id));
        return "noteUpdate";
    }

    @PostMapping("/modify")
    public String update(@Valid @ModelAttribute("note") Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "noteUpdate";
        }
        if (!noteService.update(note)) {
            model.addAttribute("error", GENERIC_ERROR);
            return "noteUpdate";
        }
        long patId = note.getPatId();
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }

    @GetMapping("/delete")
    public String delete(@Valid @ModelAttribute("id") String id, @RequestParam("patId") long patId, Model model) {
        if(noteService.delete(id)) {
            model.addAttribute("message", "note has been successfully deleted");
        }
        else {
            model.addAttribute("error", GENERIC_DELETION_ERROR);
        }
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }



}