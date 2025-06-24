package com.medilabo.frontapplication.service;

import org.springframework.stereotype.Service;
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
