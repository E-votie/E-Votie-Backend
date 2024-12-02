package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {
    Optional<Party> findByPartyName(String partyName);

    Party findBySecretaryId(String userId);
}
