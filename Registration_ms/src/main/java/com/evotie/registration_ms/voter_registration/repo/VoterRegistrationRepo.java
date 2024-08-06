package com.evotie.registration_ms.voter_registration.repo;

import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoterRegistrationRepo extends JpaRepository<VoterRegistration, String> {
    List<VoterRegistration> findByGramaNiladhariDivision(String GramaNiladhariDivision);
    List<VoterRegistration> findByGramaNiladhariDivisionAndStatus(String GramaNiladhariDivision, String status);
    VoterRegistration findByApplicationIDAndStatus(String applicationID, String Status);
    VoterRegistration findByNIC(String nic);
    VoterRegistration findByApplicationID(String ApplicationID);
    List<VoterRegistration> findByStatus(String status);
}
