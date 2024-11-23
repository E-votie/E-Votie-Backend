package com.evotie.election_ms.repo;

import com.evotie.election_ms.model.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepo extends JpaRepository<Candidates, Long> {
    List<Candidates> findByElectionId(Long electionId);
    List<Candidates> findByElectionIdAndStatus(Long electionId, String status);
}
