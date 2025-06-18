package com.medilabo.frontapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GatewayProperties {

    // authentication application

    @Value("${authentication-uri}")
    private String authenticationUri;

    // patient application

    @Value("${patients-uri}")
    private String allPatientUri;

    @Value("${patients-view-uri}")
    private String patientViewUri;

    @Value("${patients-update-uri}")
    private String patientUpdateUri;

    @Value("${patients-create-uri}")
    private String patientCreateUri;

    @Value("${patients-delete-uri}")
    private String patientDeleteUri;

    // note application

    @Value("${notes-uri}")
    private String allNotesUri;

    @Value("${notes-view-uri}")
    private String noteViewUri;

    @Value("${notes-create-uri}")
    private String noteCreateUri;

    @Value("${notes-update-uri}")
    private String noteUpdateUri;

    @Value("${notes-delete-uri}")
    private String noteDeleteUri;

    // risk application

    @Value("${risks-uri}")
    private String riskUri;

}
