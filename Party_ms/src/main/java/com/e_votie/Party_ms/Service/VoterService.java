package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.DTO.Voter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(name = "registration-ms", url = "http://localhost:8081")
public interface VoterService {

    @GetMapping("/voter/my_details")
    public ResponseEntity<?> getMyDetails(@RequestHeader("Authorization") String token);
}
