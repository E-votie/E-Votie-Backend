package com.example.partyservice.repository;

import com.example.partyservice.model.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, String> {

    List<PartyMember> findAllByParty_registrationId(Integer partyId);

    @Query("SELECT pm FROM PartyMember pm WHERE LOWER(CONCAT(pm.firstName, ' ', pm.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<PartyMember> findByFullNameContaining(String fullName);

    Optional<PartyMember> findByNIC(String nic);
}
