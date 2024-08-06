package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.DTO.SignDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterApplicationDTO;
import com.evotie.registration_ms.voter_registration.DTO.VoterRegistrationListDTO;
import com.evotie.registration_ms.voter_registration.Service.S3Service;
import com.evotie.registration_ms.voter_registration.Service.VoterService;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/voter")
@Validated
public class VoterController {

    private final VoterService voterService;
    private final S3Service s3Service;

    public VoterController(VoterService voterService, S3Service s3Service) {
        this.voterService = voterService;
        this.s3Service = s3Service;
    }

    @GetMapping("/my_details")
    public ResponseEntity<?> getMyDetails(@AuthenticationPrincipal Jwt jwt){
        String NIC = jwt.getClaimAsString("preferred_username");
        Voter voter = voterService.getMyDetails(NIC);
        if(voter != null){
            Map<String, Object> response = new HashMap<>();
            response.put("voter", voter);
            response.put("profileImageUrl", s3Service.generatePresignedUrlServise(voter.getApplicationID() + "_Face.jpg"));
            response.put("Status", voterService.getRegistrationStatus(NIC));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please contact election commission IT Department");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getMyStatus(@AuthenticationPrincipal Jwt jwt){
        String NIC = jwt.getClaimAsString("preferred_username");
        Voter voter = voterService.getMyDetails(NIC);
        if(voter != null){
            Map<String, Object> response = new HashMap<>();
            response.put("Status", voterService.getRegistrationStatus(NIC));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please contact election commission IT Department");
        }
    }

    @PostMapping("/vote")
    public ResponseEntity<?> Vote(){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "successful");
        return ResponseEntity.ok().body(response);
    }
}
