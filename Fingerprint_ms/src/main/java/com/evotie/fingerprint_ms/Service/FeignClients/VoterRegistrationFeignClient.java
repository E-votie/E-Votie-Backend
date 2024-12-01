package com.evotie.fingerprint_ms.Service.FeignClients;

import com.evotie.fingerprint_ms.models.Fingerprint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "REGISTRATION-MS")
public interface VoterRegistrationFeignClient {

    @PostMapping("/api/fingerprint/add_fingerprint")
    ResponseEntity<?> AddFingerprint(Fingerprint fingerprint);
}
