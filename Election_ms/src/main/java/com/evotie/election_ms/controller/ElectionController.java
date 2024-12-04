package com.evotie.election_ms.controller;

import com.evotie.election_ms.Service.ElectionService;
import com.evotie.election_ms.dto.PollingStationRequest;
import com.evotie.election_ms.model.Election;
import com.evotie.election_ms.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/election")
@Validated
public class ElectionController {

    @Autowired
    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> ElectionCreate(@RequestBody Election election){
        log.info("Creating election");
        return electionService.createElection(election);
    }

    @GetMapping("/get_elections/{status}")
    public ResponseEntity<?> getElectionsByStatus(@PathVariable String status){
        log.info("Fetching elections by status: {}", status);
        return electionService.getElectionsByStatus(status);
    }

    @PostMapping("/set_timeline")
    public ResponseEntity<?> setElectionTimeline(@RequestBody Election election){
        log.info("Setting election timeline");
        return electionService.setElectionTimeline(election);
    }

    @PostMapping("/set_polingstations/{electionId}")
    public ResponseEntity<?> setPolingStations(@PathVariable Long electionId, @RequestBody PollingStationRequest request){
        log.info("Setting poling stations for election: {}", electionId);
        log.info(request.getPollingStations().toString());
        return electionService.setPolingStations(electionId, request.getPollingStations());
    }

    @GetMapping("/get_candidates/{electionId}/{status}")
    public ResponseEntity<?> getCandidatesByStatus(@PathVariable Long electionId, @PathVariable String status){
        log.info("Fetching candidates by status: {}", status);
        return electionService.getCandidatesByIdAndStatus(electionId, status);
    }

    @GetMapping("/set_status_candidates/{electionId}/{selectedCandidate}/{status}")
    public ResponseEntity<?> setStatusCandidates(@PathVariable Long electionId, @PathVariable Long selectedCandidate, @PathVariable String status){
        log.info("Setting status for candidate: {}", selectedCandidate);
        return electionService.setStatusCandidates(electionId, selectedCandidate, status);
    }

    @GetMapping("deploy_contract/{electionId}")
    public ResponseEntity<?> deployContract(@PathVariable Long electionId){
        return electionService.deployContract(electionId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllElections(@AuthenticationPrincipal Jwt jwt){
        try{
            return new ResponseEntity<List<Election>>(electionService.getAllElections(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //add new candidate
    @PostMapping("/new/candidate")
    public ResponseEntity<?> addNewCandidate(@AuthenticationPrincipal Jwt jwt){
        try{
            return new ResponseEntity<>(electionService.addNewCandidate(jwt), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_candidates_info/{electionId}")
    public ResponseEntity<?> getCandidatesInfo(@PathVariable Long electionId){
        return electionService.getCandidatesInfo(electionId);
    }

    @GetMapping("/get_election/{status}")
    public ResponseEntity<?> getElectionByStatus(@PathVariable String status){
        return electionService.getElectionByStatus(status);
    }

    @GetMapping("/vote/{candidateId}")
    public ResponseEntity<?> vote(@PathVariable String candidateId){
        return electionService.vote(candidateId);
    }
}
