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

    @Value("${patient-application-uri}/")
    private String PatientUri;

    @Value("${patient-application-uri}/all")
    private String allPatientUri;

    @Value("${patient-application-uri}/create")
    private String patientCreationUri;

    @Value("${patient-application-uri}/modify")
    private String patientModificationUri;

    @Value("${patient-application-uri}/delete")
    private String patientDeletionUri;

    // note application

    @Value("${note-application-uri}/")
    private String noteUri;

    @Value("${note-application-uri}/patId")
    private String patIdNotesUri;

    @Value("${note-application-uri}/create")
    private String noteCreationUri;

    @Value("${note-application-uri}/modify")
    private String noteModificationUrl;

    @Value("${note-application-uri}/delete")
    private String noteDeletionUri;

    // risk application

    @Value("${risks-uri}")
    private String riskUri;

}
