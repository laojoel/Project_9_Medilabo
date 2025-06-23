package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import static com.medilabo.frontapplication.constant.MessageConstant.*;
import static com.medilabo.frontapplication.constant.ErrorConstant.*;
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

    @GetMapping("/create")
    public String create(@RequestParam("patId") Long patId, Model model) {
        Patient Patient = patientService.getPatientId(patId);
        if (Patient==null) {
            log.error("create note for null patient, redirect to list");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }

        Note note = new Note();
        note.setPatId(patId);
        note.setPatient(Patient.getFullName());
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
        Note createdNote = noteService.create(note);
        if (createdNote == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_CREATION);
            model.addAttribute("note", note);
            return "noteCreate";
        }

        long patId = note.getPatId();
        Patient patient = patientService.getPatientId(patId);
        if (patient==null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute(patientService.getAllPatients());
            return "patients";
        }

        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_CREATION);
        model.addAttribute("patient",  patient);
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
        Note modifiedNote = noteService.modify(note);
        if (modifiedNote == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_MODIFICATION);
        }
        else {
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_MODIFICATION);
        }
        long patId = note.getPatId();
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }

    @GetMapping("/delete")
    public String delete(@Valid @ModelAttribute("id") String id, @RequestParam("patId") long patId, Model model) {
        if(noteService.delete(id)) {
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_DELETION);
        }
        else {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_DELETION);
        }
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }



}