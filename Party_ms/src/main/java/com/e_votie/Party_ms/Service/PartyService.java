package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Address;
import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Repository.AddressRepository;
import com.e_votie.Party_ms.Repository.DocumentRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileMsClient fileMsClient;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PartyMemberService partyMemberService;

    public Party createParty(Party party, List<MultipartFile> files, Jwt jwt) throws Exception {
        String userId = jwt.getClaimAsString("preferred_username");
        party.setSecretaryId(userId);

        // Check if a party already exists with the given secretaryId
        Optional<Party> existingPartyOptional = Optional.ofNullable(partyRepository.findBySecretaryId(userId));
        if (existingPartyOptional.isPresent()) {
            throw new Exception("A party already exists with this secretary.");
        }

        // Save the party first to establish the relationship with documents
        Party savedParty = null;
        try {
            savedParty = partyRepository.save(party);
        } catch (Exception e) {
            System.err.println("Error saving party: " + e.getMessage());
            throw new Exception("Error saving party: " + e.getMessage());
        }

        // Upload files and create Document objects linked to the Party
        if (files != null && !files.isEmpty()) {
            try {
                Party finalSavedParty = savedParty;
                List<Document> documents = files.stream()
                        .map(file -> documentService.createAndSaveDocument(file, finalSavedParty))
                        .collect(Collectors.toList());
            } catch (Exception e) {
                System.err.println("Error uploading files: " + e.getMessage());
                throw new Exception("Error uploading files: " + e.getMessage());
            }
        }

        // add party secretary
        try {
            PartyMember createdPartyMember = partyMemberService.registerNewPartyOfficial(
                    String.valueOf(savedParty.getRegistrationId()), userId, party.getSecretoryName(),"Secretary", "The secretary oversees party records, coordinates communication, and ensures smooth administration."
            );
        } catch (Exception e) {
            System.err.println("Error creating party member: " + e.getMessage());
            throw new Exception("Error creating party member: " + e.getMessage());
        }

        // add party leader
        try {
            PartyMember createdPartyMember = partyMemberService.registerNewPartyOfficial(
                    String.valueOf(savedParty.getRegistrationId()), party.getLeaderId(), party.getLeaderName(),"Leader", "The leader sets the party’s vision, represents it publicly, drives its mission, and inspires members to work towards collective goals."
            );
        } catch (Exception e) {
            System.err.println("Error creating party member: " + e.getMessage());
            throw new Exception("Error creating party member: " + e.getMessage());
        }

        // Save the updated Party with linked Documents
        try {
            return partyRepository.save(savedParty);
        } catch (Exception e) {
            System.err.println("Error saving the updated party: " + e.getMessage());
            throw new Exception("Error saving the updated party: " + e.getMessage());
        }
    }


    // Method to retrieve a Party by its ID
    public Optional<Party> getPartyById(String partyId) {
        return partyRepository.findById(Integer.valueOf(partyId));
    }

    // Method to retrieve a Party by its name
    public Optional<Party> getPartyByName(String partyName) {
        return partyRepository.findByPartyName(partyName);
    }

    // Method to retrieve all Parties
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    // Method to update an existing Party
    public Party updateParty(String partyId, Party updatedParty, List<MultipartFile> files, Jwt jwt ) throws Exception {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));

        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();

            existingParty.setPartyName(updatedParty.getPartyName());
            existingParty.setAbbreviation(updatedParty.getAbbreviation());
            existingParty.setFoundedDate(updatedParty.getFoundedDate());
            existingParty.setDistrictBasisSeats(updatedParty.getDistrictBasisSeats());
            existingParty.setNationalBasisSeats(updatedParty.getNationalBasisSeats());
            existingParty.setTotalSeats(updatedParty.getTotalSeats());
            existingParty.setContactNumber(updatedParty.getContactNumber());
            existingParty.setPartyWebsite(updatedParty.getPartyWebsite());
            existingParty.setPartySymbol(updatedParty.getPartySymbol());
            existingParty.setAddress(updatedParty.getAddress());

            Party savedParty = partyRepository.save(existingParty);

            //update logo
            if (files != null && !files.isEmpty()) {
                try {
                    //delete existing document
                    documentService.deleteDocument(savedParty, "logo");

                    //save new logo
                    Party finalSavedParty = savedParty;
                    List<Document> documents = files.stream()
                            .map(file -> documentService.createAndSaveDocument(file, finalSavedParty))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.err.println("Error uploading files: " + e.getMessage());
                    throw new Exception("Error uploading files: " + e.getMessage());
                }
            }
            // Save the updated party back to the repository and return it
            return savedParty;

        } else {
            // Throw an exception if the party with the given ID is not found
            throw new Exception("Party with ID " + updatedParty.getRegistrationId() + " not found");
        }
    }

    // Method to delete a Party by its ID
    public void deleteParty(Integer partyId) throws Exception {
        if (partyRepository.existsById(partyId)) {
            partyRepository.deleteById(partyId);
        } else {
            throw new Exception("Party with ID " + partyId + " not found");
        }
    }

    //update party state
    public Party updatePartyState(String partyId, String state) throws Exception {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));

        if(existingPartyOptional.isPresent()){
            Party existingParty = existingPartyOptional.get();
            existingParty.setState(state);
            return partyRepository.save(existingParty);
        }else {
            throw new Exception("Party with ID " + partyId + " not found");
        }
    }
}
