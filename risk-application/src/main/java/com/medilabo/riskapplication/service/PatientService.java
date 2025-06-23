package com.medilabo.riskapplication.service;

import org.springframework.stereotype.Service;
import com.medilabo.riskapplication.model.Patient;
import com.medilabo.riskapplication.proxy.PatientProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientService {

    private final PatientProxy patientProxy;

    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public Patient getPatientId(long id) {
        return patientProxy.get(id);
    }
}
