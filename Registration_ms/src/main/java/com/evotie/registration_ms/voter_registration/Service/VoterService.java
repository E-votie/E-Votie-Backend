package com.evotie.registration_ms.voter_registration.Service;

import com.evotie.registration_ms.voter_registration.DTO.VoterRegistrationDTO;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VoterService {
    Voter getMyDetails(String NIC);
    String getRegistrationStatus(String NIC);
    ResponseEntity<?> nominate(String nominationNumber, String candidateNIC);
    ResponseEntity<?> electionRegister(String electionID,String voterNIC);
    Voter getVoter(String nic);
    ResponseEntity<?> isRegistered(String NIC, String electionID);
}
