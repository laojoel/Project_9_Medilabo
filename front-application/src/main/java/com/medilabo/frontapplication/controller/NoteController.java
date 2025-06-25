package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import static com.medilabo.frontapplication.constant.MessageConstant.*;
import static com.medilabo.frontapplication.constant.ErrorConstant.*;

import com.medilabo.frontapplication.service.RiskService;
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
    private final RiskService riskService;

    public NoteController(NoteService noteService, PatientService patientService, RiskService riskService) {
        this.noteService = noteService;
        this.patientService = patientService;
        this.riskService = riskService;
    }

    /*
    Display an empty 'note creation page'
    User will be redirected to the 'Patients Page' if the Note's patient ID isn't found.
     */
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
    public String create(@Valid @ModelAttribute("note") Note note, BindingResult result, Model model) {
        // verify if form inputs
        if (result.hasErrors()) {
            log.warn("note creation form has incorrect entries");
            return "noteCreate";
        }

        // verify that the Note Application successfully created the new Note
        // then we redirect user to the updated Patient detail Page

        Note createdNote = noteService.create(note);
        if (createdNote == null) {
            log.error("Note creation is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_CREATION);
            model.addAttribute("note", note);
            return "noteCreate";
        }

        long patId = note.getPatId();
        Patient patient = patientService.getPatientId(patId);
        if (patient==null) {
            // if patient doesn't exist anymore
            log.error("Patient ID" + patId + " is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute(patientService.getAllPatients());
            return "patients";
        }

        String risk = riskService.getRiskLevelForPatientId(patId);
        if (risk == null) {
            log.error("Risk for patient ID" + patId + " is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        }
        else {
            model.addAttribute("risk", risk);
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
            log.warn("Note modification entry has error");
            return "noteUpdate";
        }

        // verify that the Note Application successfully updated the Note
        Note modifiedNote = noteService.modify(note);
        if (modifiedNote == null) {
            log.error("Note ID" + note.getId() + " is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_MODIFICATION);
        }
        else {
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_NOTE_MODIFICATION);
        }

        // re-evaluate risk level since note update may affect the result
        String risk = riskService.getRiskLevelForPatientId(note.getPatId());
        if (risk == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        }
        else {
            model.addAttribute("risk", risk);
        }

        long patId = note.getPatId();
        model.addAttribute("patient",  patientService.getPatientId(patId));
        model.addAttribute("notes",  noteService.getAllNotesPatId(patId));
        return "patientView";
    }

    @GetMapping("/delete")
    public String delete(@Valid @ModelAttribute("id") String id, @RequestParam("patId") long patId, Model model) {
        if(noteService.delete(id)) {
            log.error("Note ID" + id + " is null");
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