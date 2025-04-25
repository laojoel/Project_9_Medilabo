package com.medilabo.frontapplication.controller;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PatientController {
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/patient/patients")
    public String patientList(Model model) {
        return  "to do";
    }
}
