package com.medilabo.frontapplication.proxy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GatewayRoutes {
    @Value("${authentication-uri}")
    private String authenticationUri;

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
}
