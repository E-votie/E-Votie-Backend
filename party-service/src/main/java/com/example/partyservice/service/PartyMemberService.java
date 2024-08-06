package com.example.partyservice.service;

import com.example.partyservice.model.Party;
import com.example.partyservice.model.PartyMember;
import com.example.partyservice.repository.PartyMemberRepository;
import com.example.partyservice.repository.PartyRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyMemberService {

    @Autowired
    private PartyRepostitory partyRepository;

    @Autowired
    private PartyMemberRepository partyMemberRepository;

    //Method to register a new party member
    public PartyMember registerNewPartyMember(String partyId, PartyMember partyMember) throws Exception {
        Optional<Party> partyOptional = partyRepository.findById(Integer.valueOf(partyId));

        if (partyOptional.isPresent()) {
            partyMember.setParty(partyOptional.get());
            return partyMemberRepository.save(partyMember);
        } else {
            throw new Exception("Party with ID " + partyId + " not found");
        }
    }

    //Method to retrieve party members of a given party
    public List<PartyMember> getPartyMembersByPartyId(String partyId) throws Exception {
        Optional<Party> partyOptional = partyRepository.findById(Integer.valueOf(partyId));

        if (partyOptional.isPresent()) {
            return partyMemberRepository.findAllByParty_registrationId(Integer.valueOf(partyId));
        } else {
            throw new Exception("Party with ID " + partyId + " not found");
        }
    }

    //Method to search party members by from a given member name
    public List<PartyMember> getPartyMemberByMemberName(String memberName) {
            return partyMemberRepository.findByFullNameContaining(memberName);
    }

    //Method to retrieve party member using given nic
    public PartyMember getPartyMemberByNIC(String nic) throws Exception {
        return partyMemberRepository.findByNIC(nic)
                .orElseThrow(() -> new Exception("Party Member not found"));
    }

    //Method to update the party member
    public PartyMember updatePartyMember(String nic, PartyMember updatedPartyMember) throws Exception {
        PartyMember existMember = getPartyMemberByNIC(nic);

        // Update the existing member's details
        existMember.setFirstName(updatedPartyMember.getFirstName());
        existMember.setLastName(updatedPartyMember.getLastName());
        existMember.setEmail(updatedPartyMember.getEmail());
        existMember.setRole(updatedPartyMember.getRole());
        existMember.setPhoneNumbers(updatedPartyMember.getPhoneNumbers());
        existMember.setPollingNumber(updatedPartyMember.getPollingNumber());
        existMember.setPollingDivision(updatedPartyMember.getPollingDivision());
        existMember.setGramaNiladhariDivision(updatedPartyMember.getGramaNiladhariDivision());
        existMember.setDivisionalSecretariatArea(updatedPartyMember.getDivisionalSecretariatArea());
        existMember.setElectoralDistrict(updatedPartyMember.getElectoralDistrict());

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
    public void deletePartyMember(String nic) throws Exception {
        partyMemberRepository.delete(getPartyMemberByNIC(nic));
    }



}
