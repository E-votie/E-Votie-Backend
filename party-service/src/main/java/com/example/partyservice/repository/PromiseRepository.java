package com.example.partyservice.repository;

import com.example.partyservice.model.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromiseRepository extends JpaRepository<Promise, Integer> {
}
