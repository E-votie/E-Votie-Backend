package com.evotie.registration_ms.voter_registration.impl;

import com.evotie.registration_ms.voter_registration.Service.FeignClients.HyperledgerFabricClient;
import com.evotie.registration_ms.voter_registration.Service.VoterService;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import com.evotie.registration_ms.voter_registration.repo.VoterRegistrationRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class VoterServiceImpl implements VoterService {

    VoterRepo voterRepo;
    VoterRegistrationRepo voterRegistrationRepo;
    HyperledgerFabricClient hyperledgerFabricClient;

    public VoterServiceImpl(VoterRepo voterRepo, VoterRegistrationRepo voterRegistrationRepo, HyperledgerFabricClient hyperledgerFabricClient) {
        this.voterRepo = voterRepo;
        this.voterRegistrationRepo = voterRegistrationRepo;
        this.hyperledgerFabricClient = hyperledgerFabricClient;
    }

    @Override
    public Voter getMyDetails(String NIC) {
        return (voterRepo.findByNIC(NIC));
    }

    public String getRegistrationStatus(String NIC) {
        return voterRegistrationRepo.findByNIC(NIC).getStatus();
    }

    @Override
    public ResponseEntity<?> nominate(String nominationNumber, String candidateNIC) {
        Voter voter = voterRepo.findByNIC(candidateNIC);
        if (voter == null) {
            return ResponseEntity.badRequest().body("Candidate not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("fullName", voter.getName());
        response.put("address", voter.getAddress());
        response.put("dob", voter.getDOB());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> electionRegister(String electionID, String voterNIC) {
        Voter voter = voterRepo.findByNIC(voterNIC);
        ResponseEntity<?> response = hyperledgerFabricClient.assignVoterToElection(voter.getVoterID(), electionID, true, voter.getGramaNiladhariDivision());
        log.info("Election Register: " + response.getBody());
        return response;
    }

    @Override
    public Voter getVoter(String nic) {
        return voterRepo.findByNIC(nic);
    }

    @Override
    public ResponseEntity<?> isRegistered(String NIC, String electionID) {
        Voter voter = voterRepo.findByNIC(NIC);
        return hyperledgerFabricClient.getVoterElectionRelation(voter.getVoterID(), electionID);
    }
}
