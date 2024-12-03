package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository
@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, String> {
    List<Inquiry> findByPartyId(String partyId);
}
