package com.example.partyservice.repository;

import com.example.partyservice.model.Party;
import com.example.partyservice.model.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepostitory extends JpaRepository<Party, Integer> {
    Optional<Party> findByPartyName(String partyName);
}
