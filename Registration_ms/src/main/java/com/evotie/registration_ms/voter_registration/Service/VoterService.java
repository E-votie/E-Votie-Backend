package com.evotie.registration_ms.voter_registration.Service;

import com.evotie.registration_ms.voter_registration.DTO.VoterRegistrationDTO;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import org.springframework.stereotype.Service;

@Service
public interface VoterService {
    Voter getMyDetails(String NIC);
    String getRegistrationStatus(String NIC);
}
