package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.external.Fingerprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/fingerprint")
public class FingerprintController {

    private static final Logger log = LoggerFactory.getLogger(FingerprintController.class);
    private final VoterRegistrationService voterRegistrationService;

    public FingerprintController(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
    }

    @PostMapping("/add_fingerprint")
    public ResponseEntity<?> AddFingerprint(@RequestBody Fingerprint fingerprint) {
        log.info("Received fingerprint: " + fingerprint.toString());
        try{
            String response = voterRegistrationService.addFingerprint(fingerprint);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}