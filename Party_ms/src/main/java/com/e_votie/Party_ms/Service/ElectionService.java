package com.e_votie.Party_ms.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ELECTION-MS")
public interface ElectionService {
    @GetMapping("/election/all")
    public ResponseEntity<?> getAllElections(@RequestHeader("Authorization") String bearerToken);
}
