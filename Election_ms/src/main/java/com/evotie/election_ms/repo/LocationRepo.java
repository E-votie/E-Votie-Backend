package com.evotie.election_ms.repo;

import com.evotie.election_ms.model.Election;
import com.evotie.election_ms.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Long>{
    Location findByName(String name);
    List<Location> findByElections(Election election);
}