package com.medilabo.riskapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GatewayProperties {
    @Value("${patients-uri}/view")
    private String patientViewUri;

    @Value("${notes-uri}")
    private String allNotesUri;
}
