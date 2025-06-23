package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.PatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientService {
    private final PatientProxy patientProxy;

    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public List<Patient> getAllPatients() {
        return patientProxy.getAll();
    }

    public Patient getPatientId(long id) {
        return patientProxy.get(id);
    }

    public Patient modify(Patient patient) {
        return patientProxy.modify(patient);
    }

    public Patient create(Patient patient) {
        return patientProxy.create(patient);
    }

    public boolean delete(long id) {
        Boolean result = patientProxy.delete(id);
        if (result == null) {return false;}
        return result;

    }
}
