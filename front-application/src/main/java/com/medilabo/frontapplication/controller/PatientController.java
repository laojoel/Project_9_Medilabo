package com.medilabo.frontapplication.controller;

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
@RequestMapping("/patients")
@Controller
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/view")
    public String dossierPatient(@RequestParam("id") Long id, Model model) {
        log.info("Display patient ID " + id + " folder");
        model.addAttribute("patient", patientService.getPatientId(id));
        return "patientView";
    }


}
