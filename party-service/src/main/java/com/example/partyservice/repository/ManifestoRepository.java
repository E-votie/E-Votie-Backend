package com.example.partyservice.repository;

import com.example.partyservice.model.Manifesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManifestoRepository extends JpaRepository<Manifesto, Integer> {
}
