package com.medilabo.riskapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GatewayProperties {
    @Value("${patient-application-uri}/")
    private String patientUri;

    @Value("${note-application-uri}/patId")
    private String patIdNotesUri;
}
