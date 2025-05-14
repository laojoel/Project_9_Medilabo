package com.medilabo.frontapplication.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PatientController {

    @GetMapping("/patients")
    public String home(HttpServletRequest request, Model model) {
        //model.addAttribute("patients",);
        return "patients";
    }


}
