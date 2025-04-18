package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.PatientProxy;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientProxy patientProxy;

    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public List<Patient> getPatients(String securityHeader) {

        List<Patient> patients = new ArrayList<>();
        try {
            patients = patientProxy.patients(securityHeader);
        } catch (FeignException e) {
            if (e.status() == 401) {log.warn("Authentication Failed");}
            else {log.error("FeignException " + e.status() + ": " + e.getMessage());}
        }
        return patients;
    }

}
