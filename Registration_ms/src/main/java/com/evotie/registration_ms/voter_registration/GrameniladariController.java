package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.DTO.SignDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterApplicationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/gramaniladhari")
@Validated
public class GrameniladariController {
    private final VoterRegistrationService voterRegistrationService;

    public GrameniladariController(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
    }


    @RequestMapping("/new-applications")
    public ResponseEntity<?> getVoterRegistrationBYGramaNiladhariDivision(@AuthenticationPrincipal Jwt jwt) {
//        if (!jwt.getClaimAsStringList("realm_access.roles").contains("GramaNiladhari")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
        String gnDivision = jwt.getClaimAsString("GN_Division");
        gnDivision = gnDivision.replaceAll("\\[|\\]", "");
        return ResponseEntity.ok(voterRegistrationService.getVoterRegistrationBYGramaNiladhariDivisionAndStatus(gnDivision, "Pending"));
    }

    @GetMapping("/voter_application")
    public VoterApplicationDTO getVoterRegistration(@RequestParam String applicationID) {
        log.info(applicationID);
        VoterApplicationDTO voterRegistration = voterRegistrationService.getVoterRegistration(applicationID);
        if (voterRegistration == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter registration not found");
        }
        log.info(voterRegistration.toString());
        return voterRegistration;
    }

    @PostMapping("/voter_application/sign")
    public ResponseEntity<?> VoterApplicationSign(@RequestBody SignDTO sign){
        return voterRegistrationService.grameniladariSignature(sign);
    }
}
