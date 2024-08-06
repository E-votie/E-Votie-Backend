package com.evotie.registration_ms.voter_registration.DTO;

import lombok.Data;

@Data
public class FingerprintVerifyDTO {
    private String ApplicationID;
    private String Name;
    private String NIC;
    private String Face;
    private String NICBack;
    public String NICFront;
}
