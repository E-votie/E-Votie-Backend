package com.e_votie.Party_ms.Repository;


import com.e_votie.Party_ms.Model.History;
import com.e_votie.Party_ms.Model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByParty(Party existingParty);
}
