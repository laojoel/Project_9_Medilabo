package com.medilabo.frontapplication.controller;

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
@RequestMapping("/patients")
@Controller
public class PatientController {

    private final PatientService patientService;
    private final NoteService noteService;

    public PatientController(PatientService patientService, NoteService noteService) {
        this.patientService = patientService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/view")
    public String patientView(@RequestParam("id") Long id, Model model) {
        log.info("Display patient ID " + id + " folder");
        model.addAttribute("patient", patientService.getPatientId(id));
        model.addAttribute("notes", noteService.getAllNotesPatId(id));
        return "patientView";
    }

    @GetMapping("/update")
    public String patientEdit(@RequestParam("id") Long id, Model model) {
        log.info("Modify patient ID " + id + " info");
        model.addAttribute("patient", patientService.getPatientId(id));
        return "patientUpdate";
    }

    @PostMapping("/update")
    public String patientUpdate(@Valid @ModelAttribute("patient") Patient patient, BindingResult result, Model model) {
        System.out.println("POST patientUpdate | Entry point");
        if (result.hasErrors()) {
            log.warn("patient modification form has incorrect entries");
           return "patientUpdate";
        }
        patientService.updatePatient(patient);
        model.addAttribute("patient",  patient);
        return "patientView";
    }

    @GetMapping("/create")
    public String patientCreate(Model model) {
        log.info("display creation form for patient");
        model.addAttribute("patient", new Patient());
        return "patientCreate";
    }

    @PostMapping("/create")
    public String patientCreate(@Valid @ModelAttribute("patient") Patient patient, BindingResult result,
                                Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.warn("patient creation form has incorrect entries");
            return "patientCreate";
        }
        patientService.createPatient(patient);
        model.addAttribute("patient",  patient);
        return "patientView";
    }

    @GetMapping("/delete")
    public String patientDelete(@RequestParam("id") Long id, Model model) {
        log.info("Delete patient ID " + id + " info");
        model.addAttribute("patient", patientService.getPatientId(id));
        return "patientUpdate";
    }


}
