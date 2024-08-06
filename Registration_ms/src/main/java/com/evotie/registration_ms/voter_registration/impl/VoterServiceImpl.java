package com.evotie.registration_ms.voter_registration.impl;

import com.evotie.registration_ms.voter_registration.Service.VoterService;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import com.evotie.registration_ms.voter_registration.repo.VoterRegistrationRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VoterServiceImpl implements VoterService {

    VoterRepo voterRepo;
    VoterRegistrationRepo voterRegistrationRepo;

    public VoterServiceImpl(VoterRepo voterRepo, VoterRegistrationRepo voterRegistrationRepo) {
        this.voterRepo = voterRepo;
        this.voterRegistrationRepo = voterRegistrationRepo;
    }

    @Override
    public Voter getMyDetails(String NIC) {
        return (voterRepo.findByNIC(NIC));
    }

    public String getRegistrationStatus(String NIC) {
        return voterRegistrationRepo.findByNIC(NIC).getStatus();
    }
}
