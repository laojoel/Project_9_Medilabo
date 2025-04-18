package com.medilabo.frontapplication.proxy;

import com.medilabo.frontapplication.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "gateway-application", url = "${gateway-application.url}", contextId = "gateway-application")
public interface PatientProxy {
    @GetMapping("/patient-application/patient/patients")
    List<Patient> patients(@RequestHeader("Authorization") String header);

    @GetMapping("/patient-application/patient/{id}")
    Patient getPatient(@RequestHeader("Authorization") String header, @PathVariable("id") int id);

    @PostMapping("/patient-application/patient/validate")
    Patient addPatient(@RequestHeader("Authorization") String header, @RequestBody Patient patient);

    @PostMapping("/patient-application/patient/validateUpdate")
    Patient updatePatient(@RequestHeader("Authorization") String header, @RequestBody Patient patient);

    @DeleteMapping("/patient-application/patient/delete")
    void deletePatient(@RequestHeader("Authorization") String header, @RequestBody Patient patient);
}
