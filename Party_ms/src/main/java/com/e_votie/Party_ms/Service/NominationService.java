package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.DTO.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NominationService {

    @Autowired
    private ElectionService electionService;
    public ResponseEntity<?> getAllElections(Jwt jwt) {
        String bearerToken = "Bearer " + jwt.getTokenValue();
        return electionService.getAllElections(bearerToken);
    }
}
