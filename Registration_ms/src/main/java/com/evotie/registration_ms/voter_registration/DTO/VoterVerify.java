package com.evotie.registration_ms.voter_registration.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoterVerify {
    @JsonProperty("Hash")
    private String Hash;
    @JsonProperty("OTP")
    private String OTP;
}
