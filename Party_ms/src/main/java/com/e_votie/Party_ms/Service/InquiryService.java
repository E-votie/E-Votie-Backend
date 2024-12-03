package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Inquiry;
import com.e_votie.Party_ms.Repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    public Inquiry createInquiry(Jwt jwt, Inquiry inquiry) {
        String userId = jwt.getClaimAsString("preferred_username");

        inquiry.setCreatedBy(userId);
        inquiry.setCreatedAt(String.valueOf(LocalDateTime.now()));
        return  inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquiriesByPartyId(String partyId) {
        return inquiryRepository.findByPartyId(partyId);
    }

    public Optional<Inquiry> getInquiryById(String id) {
        return inquiryRepository.findById(id);
    }

    public void deleteInquiry(String id) {
        inquiryRepository.deleteById(id);
    }
}
