package com.evotie.registration_ms.voter_registration;


import com.evotie.registration_ms.voter_registration.DTO.VoterApplicationDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterRegistrationDTO;
import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import com.evotie.registration_ms.voter_registration.DTO.VoterVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/voter-registration")
@Validated
public class VoterRegistrationController {
    private final VoterRegistrationService voterRegistrationService;

    public VoterRegistrationController(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
    }

    @PostMapping("/voter")
    public ResponseEntity<?> NewVoter(@RequestBody TempContactInfo tempContactInfo){
        return voterRegistrationService.VerifyContact(tempContactInfo);
    }

    @PostMapping("/voter/verify")
    public ResponseEntity<?> VoterVerify(@RequestBody VoterVerify voterVerify){
        return voterRegistrationService.VoterVerify(voterVerify);
    }

    @RequestMapping("/new-application")
    public VoterRegistration saveVoterRegistration(@RequestBody VoterRegistrationDTO voterRegistration) {
        return voterRegistrationService.saveVoterRegistration(voterRegistration);
    }

    @RequestMapping("/get")
    public VoterApplicationDTO getVoterRegistration(String applicationID) {
        return voterRegistrationService.getVoterRegistration(applicationID);
    }

    @RequestMapping("/verify")
    public boolean VerifyVoterRegistration(String applicationID, String Sign, String Status, String Reason) {
        return voterRegistrationService.VerifyVoterRegistration(applicationID, Sign, Status, Reason);
    }
}
