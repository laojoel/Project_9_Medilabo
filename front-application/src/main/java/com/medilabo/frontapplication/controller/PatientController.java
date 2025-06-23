package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import com.medilabo.frontapplication.service.RiskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.medilabo.frontapplication.constant.ErrorConstant.*;
import static com.medilabo.frontapplication.constant.MessageConstant.*;

@Slf4j
@RequestMapping("/patients")
@Controller
public class PatientController {

    private final PatientService patientService;
    private final NoteService noteService;
    private final RiskService riskService;

    public PatientController(PatientService patientService, NoteService noteService, RiskService riskService) {
        this.patientService = patientService;
        this.noteService = noteService;
        this.riskService = riskService;
    }

    @GetMapping()
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/view")
    public String patientView(@RequestParam("id") Long id, Model model) {
        log.info("Display patient ID " + id + " folder");

        Patient patient = patientService.getPatientId(id);
        if (patient == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute(patientService.getAllPatients());
            return "patients";
        }
        model.addAttribute("patient", patient);

        String risk = riskService.getRiskLevelForPatientId(id);
        if (risk == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", "unavailable");
        }
        else {
            model.addAttribute("risk", risk);
        }

        List<Note> notes = noteService.getAllNotesPatId(id);
        if (notes == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_NOT_FOUND);
        }
        else {
            model.addAttribute("notes", notes);
        }
        return "patientView";
    }

    @GetMapping("/modify")
    public String patientEdit(@RequestParam("id") Long id, Model model) {
        log.info("Modify patient ID " + id + " info");
        Patient patient = patientService.getPatientId(id);
        if (patient == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            return "patientView";
        }
        model.addAttribute("patient", patient);
        return "patientUpdate";
    }

    @PostMapping("/modify")
    public String patientUpdate(@Valid @ModelAttribute("patient") Patient patient, BindingResult result, Model model) {
        System.out.println("POST patientUpdate | Entry point");
        if (result.hasErrors()) {
            log.warn("patient modification form has incorrect entries");
           return "patientUpdate";
        }
        Patient modifiedPatient = patientService.modify(patient);
        if(modifiedPatient == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_MODIFICATION);
            model.addAttribute("patient", patient);
            return "patientUpdate";
        }
        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_MODIFICATION);
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
        Patient createdPatient = patientService.create(patient);
        if (createdPatient == null) {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_CREATION);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }
        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_CREATION);
        model.addAttribute("patient",  createdPatient);
        return "patientView";
    }

    @GetMapping("/delete")
    public String patientDelete(@RequestParam("id") Long id, Model model) {
        log.info("Delete patient ID " + id + " info");
        if(patientService.delete(id)) {
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_DELETION);
        }
        else {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_DELETION);
        }
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }


}
