package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Address;
import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Repository.AddressRepository;
import com.e_votie.Party_ms.Repository.DocumentRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Party createParty(Party party, List<MultipartFile> files, Jwt jwt) {
        String userId = jwt.getClaimAsString("sub");
        party.setSecretaryId(userId);

        // Save address if present
        if (party.getAddress() != null) {
            Address savedAddress = addressRepository.save(party.getAddress());
            party.setAddress(savedAddress);
        }

        // Save the party first to establish the relationship with documents
        Party savedParty = partyRepository.save(party);

        // Upload files and create Document objects linked to the Party
        if (files != null && !files.isEmpty()) {
            List<Document> documents = files.stream()
                    .map(file -> documentService.createAndSaveDocument(file, savedParty))
                    .collect(Collectors.toList());

//            // Modify the collection in place to avoid replacing it
//            List<Document> currentDocuments = savedParty.getDocuments();
//            currentDocuments.clear(); // Clear current documents
//            currentDocuments.addAll(documents); // Add new documents
//            // Set documents to the party
//            savedParty.setDocuments(documents);
        }

        // Save the updated Party with linked Documents
        return partyRepository.save(savedParty);
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
    public Party updateParty(Party updatedParty) throws Exception {
        Optional<Party> existingPartyOptional = partyRepository.findById(updatedParty.getRegistrationId());

        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();

            existingParty.setPartyName(updatedParty.getPartyName());
            existingParty.setAbbreviation(updatedParty.getAbbreviation());
            existingParty.setFoundedDate(updatedParty.getFoundedDate());
            existingParty.setPartyColors(updatedParty.getPartyColors());
            existingParty.setDistrictBasisSeats(updatedParty.getDistrictBasisSeats());
            existingParty.setNationalBasisSeats(updatedParty.getNationalBasisSeats());
            existingParty.setTotalSeats(updatedParty.getTotalSeats());
            existingParty.setContactNumber(updatedParty.getContactNumber());
            existingParty.setPartyWebsite(updatedParty.getPartyWebsite());
            existingParty.setPartySymbol(updatedParty.getPartySymbol());
            existingParty.setState(updatedParty.getState());
            existingParty.setLeaderId(updatedParty.getLeaderId());
            existingParty.setSecretaryId(updatedParty.getSecretaryId());


            existingParty.setAddress(updatedParty.getAddress());


            // Save the updated party back to the repository and return it
            return partyRepository.save(existingParty);

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

}
