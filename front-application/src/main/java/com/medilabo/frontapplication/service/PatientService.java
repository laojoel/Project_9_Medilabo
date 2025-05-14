package com.medilabo.frontapplication.service;

import com.medilabo.frontapplication.model.Patient;
import com.medilabo.frontapplication.proxy.GatewayRoutes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class PatientService {
    private final RestTemplate authRestTemplate;
    private final GatewayRoutes routes;

    public PatientService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, GatewayRoutes routes) {
        this.authRestTemplate = authRestTemplate;
        this.routes = routes;
    }

    public List<Patient> getAllPatients() {
        //log.info("Attempt to fetch list of all patients");
        //List<Patient> patients = authRestTemplate.getForObject(routes.getAllPatientUri(), List.class);
        return null;
    }
}
