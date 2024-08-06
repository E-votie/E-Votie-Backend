package com.evotie.election_ms.controller;

import com.evotie.election_ms.Service.VortingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/voting")
@Validated
public class VotingController {

    private final VortingService vortingService;

    public VotingController(VortingService vortingService) {
        this.vortingService = vortingService;
    }

//    @GetMapping("/get_all")
//    public ResponseEntity<?> getAllCandidates() {
//
//    }
}
