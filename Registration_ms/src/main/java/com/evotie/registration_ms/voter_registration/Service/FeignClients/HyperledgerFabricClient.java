package com.evotie.registration_ms.voter_registration.Service.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HYPERLEDGERFABRICCLIENT-MS")
public interface HyperledgerFabricClient {

    @PostMapping("/api/HyperlegerFabric/createVoter")
    ResponseEntity<String> createVoter(
            @RequestParam("NIC") String NIC,
            @RequestParam("Name") String Name,
            @RequestParam("voterID") String voterID,
            @RequestParam("BiometricTemplate") String BiometricTemplate
    );

    @GetMapping("/api/HyperlegerFabric/getVoter")
    ResponseEntity<String> getVoter(@RequestParam("voterID") String voterID);

    @PostMapping("/api/HyperlegerFabric/assignVoterToElection")
    ResponseEntity<String> assignVoterToElection(
            @RequestParam("voterID") String voterID,
            @RequestParam("electionID") String electionID,
            @RequestParam("eligibility") boolean eligibility,
            @RequestParam("pollingStation") String pollingStation
    );

    @PostMapping("/api/HyperlegerFabric/createElection")
    ResponseEntity<String> createElection(
            @RequestParam("contractAddress") String contractAddress,
            @RequestParam("electionID") String electionID,
            @RequestParam("endTime") String endTime,
            @RequestParam("startTime") String startTime
    );

    @GetMapping("/api/HyperlegerFabric/recordVote")
    ResponseEntity<String> recordVote(
            @RequestParam("voterID") String voterID,
            @RequestParam("electionID") String electionID
    );

    @GetMapping("/api/HyperlegerFabric/getElection")
    ResponseEntity<String> getElection(@RequestParam("electionID") String electionID);

    @GetMapping("/api/HyperlegerFabric/getVoterElectionRelation")
    ResponseEntity<String> getVoterElectionRelation(
            @RequestParam("voterID") String voterID,
            @RequestParam("electionID") String electionID
    );
}

