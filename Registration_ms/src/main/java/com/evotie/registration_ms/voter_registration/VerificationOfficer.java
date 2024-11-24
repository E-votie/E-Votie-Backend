package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.DTO.SignDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterApplicationDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterRegistrationListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/verification_officer")
@Validated
public class VerificationOfficer {
    private final VoterRegistrationService voterRegistrationService;

    public VerificationOfficer(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
    }

    @GetMapping("/get_voter_applications")
    public ResponseEntity<?> getVoterRegistration() {
        List<VoterRegistrationListDTO> voterRegistration = voterRegistrationService.getVoterRegistrationByStatus("Grameniladari_Signed");
        if (voterRegistration == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter registration not found");
        }
        return ResponseEntity.ok(voterRegistration);
    }

    @PostMapping("/voter_application/sign")
    public ResponseEntity<?> VoterApplicationSign(@RequestBody SignDTO sign){
        return voterRegistrationService.verificationOfficerSignature(sign);
    }

    @GetMapping("/fingerprint_otp/sent")
    public ResponseEntity<?> fingerprintOTPSent(@RequestParam String NIC){
        return voterRegistrationService.sentOTP(NIC);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyOTP(@RequestParam String OTP){
        return voterRegistrationService.verifyOTP(OTP);
    }

    @GetMapping("/voter_details/{voterID}")
    public ResponseEntity<?> getVoterDetailsPollingStationVerification(@PathVariable String voterID){
        return voterRegistrationService.getVoterDetailsPollingStationVerification(voterID);
    }

}
