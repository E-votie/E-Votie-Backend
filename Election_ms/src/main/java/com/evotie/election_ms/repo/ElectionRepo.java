package com.evotie.election_ms.repo;

import com.evotie.election_ms.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElectionRepo extends JpaRepository<Election, Long> {
    List<Election> findByStatus(String status);
    List<Election> findByStatusNotIn(List<String> status);
}
