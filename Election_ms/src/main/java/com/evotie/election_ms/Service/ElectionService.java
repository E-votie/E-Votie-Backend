package com.evotie.election_ms.Service;

import com.evotie.election_ms.model.Election;
import com.evotie.election_ms.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElectionService {
    public ResponseEntity<?> addNewCandidate(Jwt jwt);

    public ResponseEntity<?> getElectionsByStatus(String status);
    public ResponseEntity<?> createElection(Election election);
    public ResponseEntity<?> setElectionTimeline(Election election);
    public ResponseEntity<?> setPolingStations(Long electionId, List<Location> polingStations);
    public ResponseEntity<?> getCandidatesByIdAndStatus(Long electionId, String status);
    public ResponseEntity<?> setStatusCandidates(Long electionId, Long candidateId, String status);
    public ResponseEntity<?> deployContract(Long electionId);
    public ResponseEntity<?> getCandidatesInfo(Long electionId);
    public ResponseEntity<?> getElectionByStatus(String electionId);
    public ResponseEntity<?> vote(String electionId);
    List<Election> getAllElections();
}
