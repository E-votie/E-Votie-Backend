package com.evotie.registration_ms.voter_registration.repo;

import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepo extends JpaRepository<Voter, Long> {
    Voter findByNIC(String nic);
}
