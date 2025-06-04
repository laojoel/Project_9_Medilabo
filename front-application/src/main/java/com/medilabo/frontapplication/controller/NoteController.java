package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("/notes")
@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/view")
    public String dossierPatient(@RequestParam("id") String patId, Model model) {
        log.info("Display patient ID " + patId + " folder");
        model.addAttribute("note", noteService.getNotePatId(patId));
        return "noteView";
    }

}