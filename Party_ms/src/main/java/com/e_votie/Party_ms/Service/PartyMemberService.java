package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Repository.PartyMemberRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PartyMemberService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMemberRepository partyMemberRepository;

    @Autowired
    private VoterService voterService;

    //Method to register a new party member
    public PartyMember registerNewPartyMember(String partyId, String voterId, String role) throws Exception {
        Optional<Party> partyOptional = partyRepository.findById(Integer.valueOf(partyId));

        if (partyOptional.isPresent()) {
            PartyMember newPartyMember = new PartyMember();
            newPartyMember.setPartyMemberId(voterId);
            newPartyMember.setNIC(voterId);
            newPartyMember.setRole(role);
            newPartyMember.setParty(partyOptional.get());

            return partyMemberRepository.save(newPartyMember);
        } else {
            throw new Exception("Party with ID " + partyId + " not found");
        }
    }

    //Method to retrieve logged party member using given nic
    public PartyMember getPartyMemberByToken(Jwt jwt) throws Exception {
        String nic = jwt.getClaimAsString("preferred_username");
        return partyMemberRepository.findByNIC(nic)
                .orElseThrow(() -> new Exception("Party Member not found"));
    }

    //Method to retrieve party member using given nic
    public PartyMember getPartyMemberByNIC(String nic) throws Exception {
        return partyMemberRepository.findByNIC(nic)
                .orElseThrow(() -> new Exception("Party Member not found"));    }

    //Method to update the party member
    public PartyMember updatePartyMember(Jwt jwt, PartyMember updatedPartyMember) throws Exception {
        PartyMember existMember = getPartyMemberByToken(jwt);

        // Update the existing member's details
//        existMember.setFirstName(updatedPartyMember.getFirstName());
//        existMember.setLastName(updatedPartyMember.getLastName());
//        existMember.setEmail(updatedPartyMember.getEmail());
//        existMember.setRole(updatedPartyMember.getRole());
//        existMember.setPhoneNumbers(updatedPartyMember.getPhoneNumbers());
//        existMember.setPollingNumber(updatedPartyMember.getPollingNumber());
//        existMember.setPollingDivision(updatedPartyMember.getPollingDivision());
//        existMember.setGramaNiladhariDivision(updatedPartyMember.getGramaNiladhariDivision());
//        existMember.setDivisionalSecretariatArea(updatedPartyMember.getDivisionalSecretariatArea());
//        existMember.setElectoralDistrict(updatedPartyMember.getElectoralDistrict());

        // Check if the party needs to be updated
        if (updatedPartyMember.getParty() != null) {
            Optional<Party> partyOptional = partyRepository.findById(updatedPartyMember.getParty().getRegistrationId());
            if (partyOptional.isPresent()) {
                existMember.setParty(partyOptional.get());
            } else {
                throw new Exception("Party with ID " + updatedPartyMember.getParty().getRegistrationId() + " not found");
            }
        }

        // Save the updated member
        return partyMemberRepository.save(existMember);
    }

    //Method to delete a party member
    public void deletePartyMember(Jwt jwt) throws Exception {
        partyMemberRepository.delete(getPartyMemberByToken(jwt));
    }

}