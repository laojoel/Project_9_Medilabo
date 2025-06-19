package com.medilabo.riskapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import com.medilabo.riskapplication.service.RiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/risk")
public class RiskController {

    private final RiskService riskService;

    @GetMapping("/{patId}")
    public ResponseEntity<String> notesPatient(@PathVariable("patId") int patId) {
        log.info("risk evaluation for patient ID {}", patId);

        String result = riskService.riskEvaluation(patId);
        if (result == null) {ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();}
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
