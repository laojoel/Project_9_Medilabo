package com.medilabo.riskapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.riskapplication.service.RiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class RiskController {

    private final RiskService riskService;

    @GetMapping("{patId}")
    public ResponseEntity<String> notesPatient(@PathVariable("patId") int patId) {
        log.info("risk evaluation for patient ID {}", patId);
        return ResponseEntity.status(HttpStatus.OK).body(riskService.riskEvaluation(patId));
    }

}
