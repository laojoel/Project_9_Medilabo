package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/view")
    public String dossierPatient(@RequestParam("patId") String patId, Model model) {
        log.info("Display patient ID " + patId + " folder");
        model.addAttribute("note", noteService.getNotePatId(patId));
        return "noteView";
    }

    @GetMapping("/create")
    public String patientCreate(@RequestParam("patId") Long patId, Model model) {
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
    public String patientCreate(@Valid @ModelAttribute("note") Note note, BindingResult result,
                                Model model, HttpServletRequest request) {


        if (result.hasErrors()) {
            log.warn("note creation form has incorrect entries");
            return "noteCreate";
        }

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("Create Note for PID " + note.getPatId() + " | fullName = " + note.getPatient() +
                " | note content = " + note.getContent());

        noteService.create(note);
        Patient patient = patientService.getPatientId(note.getPatId());

        model.addAttribute("patient",  patient);
        return "patientView";
    }




}