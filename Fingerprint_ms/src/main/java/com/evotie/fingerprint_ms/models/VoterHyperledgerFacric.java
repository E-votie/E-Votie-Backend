package com.evotie.fingerprint_ms.models;

import lombok.Data;

@Data
public class VoterHyperledgerFacric {
    private String nic;
    private String name;
    private String voterId;
    private String biometricTemplate;
}
