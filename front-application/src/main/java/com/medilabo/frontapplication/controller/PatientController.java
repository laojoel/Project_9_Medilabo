package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.NoteService;
import com.medilabo.frontapplication.service.PatientService;
import com.medilabo.frontapplication.service.RiskService;
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
    public String home(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/view")
    public String patientView(@RequestParam("id") Long id, Model model) {

        Patient patient = patientService.getPatientId(id);
        if (patient == null) {
            log.error("Patient id " + id + "is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }
        model.addAttribute("patient", patient);

        String risk = riskService.getRiskLevelForPatientId(id);
        if (risk == null) {
            log.error("risk patient id " + id + "is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
            model.addAttribute("risk", UNAVAILABLE);
        }
        else {
            model.addAttribute("risk", risk);
        }

        List<Note> notes = noteService.getAllNotesPatId(id);
        if (notes == null) {
            log.error("notes for patient id " + id + "is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_NOT_FOUND);
        }
        else {
            model.addAttribute("notes", notes);
        }
        return "patientView";
    }

    @GetMapping("/modify")
    public String modifyPatient(@RequestParam("id") Long id, Model model) {
        Patient patient = patientService.getPatientId(id);
        if (patient == null) {
            log.info("Patient ID " + id + " is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_NOT_FOUND);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients"; // send the user to the patients list
        }
        model.addAttribute("patient", patient);
        return "patientUpdate"; // display the patient details to modify
    }

    @PostMapping("/modify")
    public String modifyPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("patient modification form has incorrect entries");
           return "patientUpdate";
        }
        Patient modifiedPatient = patientService.modify(patient);
        if(modifiedPatient == null) {
            log.info("Patient ID " + patient.getId() + " info");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_MODIFICATION);
            model.addAttribute("patient", patient);
            return "patientUpdate";
        }

        long patId = patient.getId();
        List<Note> notes = noteService.getAllNotesPatId(patId);
        if (notes == null) {
            log.error("notes for patient id " + patId + "is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_NOTE_NOT_FOUND);
            model.addAttribute("risk", UNAVAILABLE);
        }
        else {
            model.addAttribute("notes", notes);
            // re-evaluate the risk level since age and gender modification may affect the result
            String risk = riskService.getRiskLevelForPatientId(patId);
            if (risk == null) {
                model.addAttribute(ERROR_ATTRIBUTE, ERROR_RISK);
                model.addAttribute("risk", UNAVAILABLE);
            }
            else {
                model.addAttribute("risk", risk);
            }
        }

        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_MODIFICATION);
        model.addAttribute("patient",  patient);
        return "patientView";
    }

    @GetMapping("/create")
    public String createPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientCreate";
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            log.warn("patient creation form has incorrect entries");
            return "patientCreate";
        }

        Patient createdPatient = patientService.create(patient);
        if (createdPatient == null) {
            // redirect to Patients list if creation failed due to technical issue
            log.info("Patient ID " + patient.getId() + " is null");
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_CREATION);
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }

        model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_CREATION);
        model.addAttribute("patient",  createdPatient);
        // Patient's creation has no notes, so risk will always be 'None' at first.
        model.addAttribute("risk", "None");
        return "patientView";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam("id") Long id, Model model) {
        if(patientService.delete(id)) {
            log.info("Patient ID " + id + " is null");
            model.addAttribute(MESSAGE_ATTRIBUTE, SUCCESS_PATIENT_DELETION);
        }
        else {
            model.addAttribute(ERROR_ATTRIBUTE, ERROR_PATIENT_DELETION);
        }
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }


}
