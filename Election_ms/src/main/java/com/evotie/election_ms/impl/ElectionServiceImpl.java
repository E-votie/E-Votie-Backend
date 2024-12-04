package com.evotie.election_ms.impl;

import com.evotie.election_ms.Service.ElectionService;
import com.evotie.election_ms.dto.ResponseDTO;
import com.evotie.election_ms.model.Candidates;
import com.evotie.election_ms.model.Election;
import com.evotie.election_ms.model.Location;
import com.evotie.election_ms.repo.CandidateRepo;
import com.evotie.election_ms.repo.ElectionRepo;
import com.evotie.election_ms.repo.LocationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepo electionRepo;
    private final LocationRepo locationRepo;
    private final CandidateRepo candidateRepo;

    public ElectionServiceImpl(ElectionRepo electionRepo, LocationRepo locationRepo, CandidateRepo candidateRepo) {
        this.electionRepo = electionRepo;
        this.locationRepo = locationRepo;
        this.candidateRepo = candidateRepo;
    }

    @Value("${VotingContract_URL}")
    private String votingContractUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity<?> addNewCandidate(Jwt jwt) {
        return null;
    }

    @Override
    public ResponseEntity<?> getElectionsByStatus(String status) {
        List<Election> elections = new ArrayList<>();
        if ("ongoing".equals(status)) {
            elections = electionRepo.findByStatusNotIn(List.of("Scheduled", "Complete"));
        } else if ("upcoming".equals(status)) {
            elections = electionRepo.findByStatus("Scheduled");
        } else if ("past".equals(status)) {
            elections = electionRepo.findByStatus("Complete");
        } else {
            return ResponseEntity.badRequest().body(new ResponseDTO("Invalid status", "400", null));
        }
        log.info(elections.toString());
        ResponseDTO responseDTO = new ResponseDTO("Election fetched successfully", "200", elections);
        return ResponseEntity.ok().body(responseDTO);
    }

    @Override
    public ResponseEntity<?> createElection(Election election) {
        log.info(election.toString());
        election.setElectionPublishDate(LocalDateTime.now());
        election.setStatus("Scheduled");
        Election elec = electionRepo.save(election);
        log.info(elec.toString());
        return ResponseEntity.ok().body(new ResponseDTO("Election created successfully", "200", elec));
    }

    @Override
    public ResponseEntity<?> setElectionTimeline(Election election) {
        log.info(election.toString());
        Election elec = electionRepo.findById(election.getId()).orElse(null);
        if (elec == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        elec.setElectionNominationCallingStartDate(election.getElectionNominationCallingStartDate());
        elec.setElectionNominationCallingEndDate(election.getElectionNominationCallingEndDate());
        elec.setElectionVoterRegistrationEndDate(election.getElectionVoterRegistrationEndDate());
        elec.setElectionVoterRegistrationStartDate(election.getElectionVoterRegistrationStartDate());
        elec.setElectionCampaignStartDate(election.getElectionCampaignStartDate());
        elec.setElectionCampaignEndDate(election.getElectionCampaignEndDate());
        elec.setElectionDayStartDate(election.getElectionDayStartDate());
        elec.setElectionDayEndDate(election.getElectionDayEndDate());
        elec.setElectionEndDate(election.getElectionEndDate());
        log.info(elec.toString());
        Election updatedElection = electionRepo.save(elec);
        return ResponseEntity.ok().body(new ResponseDTO("Election timeline set successfully", "200", updatedElection));
    }

    @Override
    public ResponseEntity<?> setPolingStations(Long electionId, List<Location> polingStations) {
        log.info("Setting polling stations for election: {}", electionId);
        Election election = electionRepo.findById(electionId).orElse(null);
        if (election == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        for (Location location : polingStations) {
            if (location.getId() == null) {
                locationRepo.save(location);  // Assuming you have a locationRepo for saving Location entities
            }
        }

        // Add the locations to the election
        election.setLocations(new HashSet<>(polingStations));

        // Save the updated election with the locations
        Election updatedElection = electionRepo.save(election);

        // Return success response
        return ResponseEntity.ok().body(new ResponseDTO("Polling stations set successfully", "200", updatedElection));
    }

    @Override
    public ResponseEntity<?> getCandidatesByIdAndStatus(Long electionId, String status) {
        log.info("Fetching candidates by status: {}", status);
        Election election = electionRepo.findById(electionId).orElse(null);
        if (election == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        // Assuming you have a method in the ElectionRepo to fetch candidates by electionId and status
        List<Candidates> candidates = candidateRepo.findByElectionIdAndStatus(electionId, status);
        return ResponseEntity.ok().body(new ResponseDTO("Candidates fetched successfully", "200", candidates));
    }

    @Override
    public ResponseEntity<?> setStatusCandidates(Long electionId, Long candidateId, String status) {
        log.info("Setting status for candidate: {}", candidateId);
        Election election = electionRepo.findById(electionId).orElse(null);
        if (election == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        Candidates candidate = candidateRepo.findById(candidateId).orElse(null);
        if (candidate == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Candidate not found", "400", null));
        }
        candidate.setStatus(status);
        Candidates updatedCandidate = candidateRepo.save(candidate);
        return ResponseEntity.ok().body(new ResponseDTO("Candidate status updated successfully", "200", updatedCandidate));
    }

    @Override
    public ResponseEntity<?> deployContract(Long electionId) {
        Election election = electionRepo.findById(electionId).orElse(null);
        log.info("Deploying contract for election: {}", election);
        if (election == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        List<String> candidateIds = candidateRepo.findByElectionId(electionId).stream()
                .map(Candidates::getNumber)
                .collect(Collectors.toList());
        String contractDetails = publishContract(election.getElectionDayStartDate(), election.getElectionDayEndDate(), candidateIds);
        String contractAddress = contractDetails.substring(27, 69);
        log.info("Contract address: {}", contractAddress);
        if (contractAddress == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Error deploying contract", "400", null));
        }
        election.setContractAddress(contractAddress);
        Election updatedElection = electionRepo.save(election);
        return ResponseEntity.ok().body(new ResponseDTO("Contract deployed successfully", "200", updatedElection));
    }

    @Override
    public List<Election> getAllElections() {
        return electionRepo.findAll();
    }

    @Override
    public ResponseEntity<?> getCandidatesInfo(Long electionId) {
//        List<Candidates> candidates = candidateRepo.findByElectionIdAndStatus(electionId, "Accepted");
//        Map<String, ?> candidateImageMap = new HashMap<>();
//        for (Candidates candidate : candidates) {
//            // Extract candidate id and images (assuming getImage() and getPartyImage() return URLs or paths to the images)
//            String candidateId = candidate.get;
//            String candidateImage = candidate.getImage();    // Candidate image URL or path
//            String partyImage = candidate.getPartyImage();   // Party image URL or path
//
//            // Create a map for each candidate's images
//            Map<String, String> images = new HashMap<>();
//            images.put("candidateImage", candidateImage);
//            images.put("partyImage", partyImage);
//
//            // Put candidate id as key and the images map as value
//            candidateImageMap.put(candidateId, images);
//        }
//        if (candidates != null) {
//            return ResponseEntity.ok().body(new ResponseDTO("Candidates fetched successfully", "200", candidates));
//        }
        return null;
    }

    private String publishContract(LocalDateTime electionDayStartDate, LocalDateTime electionDayEndDate, List<String> candidateIds) {
        try {
            // Convert LocalDateTime to Unix timestamp (seconds)
            long votingStartTimestamp = electionDayStartDate.toEpochSecond(ZoneOffset.UTC);
            long votingEndTimestamp = electionDayEndDate.toEpochSecond(ZoneOffset.UTC);

            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("candidateNumbers", candidateIds);
            requestBody.put("votingStart", votingStartTimestamp);
            requestBody.put("votingEnd", votingEndTimestamp);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            log.info("Deploying contract with request: {}", requestBody);

            RestTemplate restTemplate = new RestTemplate();
            // Make a POST request to the contract deployment endpoint
            return restTemplate.postForObject("http://localhost:3000/deploy-contract", entity, String.class);
        } catch (Exception e) {
            log.error("Error deploying contract", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> getElectionByStatus(String status) {
        Election election = electionRepo.findByStatus(status).get(0);
        if (election == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Election not found", "400", null));
        }
        return ResponseEntity.ok().body(new ResponseDTO("Election fetched successfully", "200", election));
    }

    @Override
    public ResponseEntity<?> vote(String CandidateID) {
        long electionId = 1;
        Election election = electionRepo.findById(electionId).orElse(null);
        log.info("Voting for candidate: {}", election.getContractAddress());
        String url = votingContractUrl;
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("contractAddress", election.getContractAddress());
        inputNode.put("candidateIndex", CandidateID);
        log.info("Assign Voter To Election: " + inputNode.toString());
        return sendPostRequest(url, inputNode);
    }

    private ResponseEntity<String> sendPostRequest(String url, ObjectNode inputNode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(inputNode);
        } catch (Exception e) {
            throw new RuntimeException("Error creating JSON request body", e);
        }
        log.info(requestBody);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }


}
