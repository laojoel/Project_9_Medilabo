package com.medilabo.frontapplication.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.frontapplication.configuration.GatewayProperties;
import com.medilabo.frontapplication.model.Note;
import com.medilabo.frontapplication.proxy.RiskProxy;

@Service
public class RiskService {

    private final RiskProxy riskProxy;

    public RiskService(RiskProxy riskProxy) {
        this.riskProxy = riskProxy;
    }

    public String getRiskLevelForPatientId(long patId) {
        return riskProxy.getRiskForPatId(patId);

    }


}
