package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByPartyRegistrationId(Integer partyRegistrationId);

    List<Request> findByReceiverNIC(String receiverNic);

    @Query(value = "SELECT * FROM request WHERE party_registration_id = :partyRegistrationId AND receivernic = :receiverNic", nativeQuery = true)
    Request findByPartyAndReceiverNIC(Integer partyRegistrationId, String receiverNic);
}
