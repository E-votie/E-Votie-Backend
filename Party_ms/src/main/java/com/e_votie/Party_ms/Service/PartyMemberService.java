package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Model.Request;
import com.e_votie.Party_ms.Repository.PartyMemberRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
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

    //register a new party member
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

    //retrieve logged party member using given nic
    public PartyMember getPartyMemberByToken(Jwt jwt) throws Exception {
        String nic = jwt.getClaimAsString("preferred_username");
        return partyMemberRepository.findByNIC(nic)
                .orElseThrow(() -> new Exception("Party Member not found"));
    }

    //retrieve party member using given nic
    public PartyMember getPartyMemberByNIC(String nic) throws Exception {
        return partyMemberRepository.findByNIC(nic)
                .orElseThrow(() -> new Exception("Party Member not found"));    }

    //update the party member
    public PartyMember updatePartyMember(Jwt jwt, PartyMember updatedPartyMember) throws Exception {
        PartyMember existMember = getPartyMemberByToken(jwt);

        // Update the existing member's details

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

    void processPartyMember(Request existingRequest, PartyMember partyMember, String profilePictureURL) {
        Optional<PartyMember> existingPartyMemberOptional =
                partyMemberRepository.findByNIC(existingRequest.getReceiverNIC());

        if (existingPartyMemberOptional.isPresent()) {
            updateExistingPartyMember(existingPartyMemberOptional.get(),
                    existingRequest,
                    partyMember,
                    profilePictureURL);
        } else {
            createNewPartyMember(existingRequest, partyMember, profilePictureURL);
        }
    }

    private void updateExistingPartyMember(PartyMember existingPartyMember,
                                           Request existingRequest,
                                           PartyMember newPartyMember,
                                           String profilePictureURL) {
        // Check if the party member is from the default party
        if (existingPartyMember.getParty().getRegistrationId() != 2000) {
            throw new IllegalStateException("Member is already associated with another party.");
        }

        // Update party member details
        existingPartyMember.setPartyMemberName(existingRequest.getReceiverName());
        existingPartyMember.setRole(newPartyMember.getRole());

        if (StringUtils.hasText(profilePictureURL)) {
            existingPartyMember.setProfilePicture(profilePictureURL);
        }

        existingPartyMember.setPartyMemberDescription(newPartyMember.getPartyMemberDescription());

        Party party = existingRequest.getParty();
        if (party == null || party.getRegistrationId() == null) {
            throw new IllegalStateException("Associated Party is not valid.");
        }else{
            existingPartyMember.setParty(party);
        }

        partyMemberRepository.save(existingPartyMember);
    }

    //create new party member
    private void createNewPartyMember(Request existingRequest,
                                      PartyMember newPartyMember,
                                      String profilePictureURL) {
        PartyMember partyMember = new PartyMember();
        partyMember.setPartyMemberId(existingRequest.getReceiverNIC());
        partyMember.setNIC(existingRequest.getReceiverNIC());
        partyMember.setRole(newPartyMember.getRole());
        partyMember.setPartyMemberName(existingRequest.getReceiverName());
        partyMember.setPartyMemberDescription(newPartyMember.getPartyMemberDescription());

        if (StringUtils.hasText(profilePictureURL)) {
            partyMember.setProfilePicture(profilePictureURL);
        }

        partyMember.setManifestos(new ArrayList<>());
        partyMember.setTopics(new ArrayList<>());

        Party party = existingRequest.getParty();
        if (party == null || party.getRegistrationId() == null) {
            throw new IllegalStateException("Associated Party is not valid.");
        }else{
            partyMember.setParty(party);
        }

        partyMemberRepository.save(partyMember);
    }

    public List<PartyMember> getPartyMembersByPartyId(String partyId) throws Exception {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if(existingPartyOptional.isPresent()){
            return partyMemberRepository.findByParty(existingPartyOptional.get());
        }else{
            throw new Exception("Party is not found");
        }
    }
}