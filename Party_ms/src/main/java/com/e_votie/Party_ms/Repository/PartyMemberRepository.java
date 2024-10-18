package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, String> {
    
    Optional<PartyMember> findByNIC(String nic);
//    Optional<PartyMember> findByVoterID(String voterId);

//    List<PartyMember> findByParty(Party party);
}
