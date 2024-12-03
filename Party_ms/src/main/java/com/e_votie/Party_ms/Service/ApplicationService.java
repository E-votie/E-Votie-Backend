package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Address;
import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Repository.AddressRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private DocumentService documentService;

    // Update Party Name
    public Party updatePartyName(String partyId, Party party) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            String previousName = existingParty.getPartyName();
            String newName = party.getPartyName();
            historyService.addNewHistoryEntry(existingParty, previousName, newName, "Party name");
            existingParty.setState("pending");
            existingParty.setPartyName(newName);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Abbreviation
    public Party updateAbbreviation(String partyId, Party party) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            String previousAbbreviation = existingParty.getAbbreviation();
            String newAbbreviation = party.getAbbreviation();
            historyService.addNewHistoryEntry(existingParty, previousAbbreviation, newAbbreviation, "Party abbreviation");
            existingParty.setState("pending");
            existingParty.setAbbreviation(newAbbreviation);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Founded Date
    public Party updateFoundedDate(String partyId, Party party) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            String previousDate = existingParty.getFoundedDate().toString();
            String newDate = party.getFoundedDate().toString();
            historyService.addNewHistoryEntry(existingParty, previousDate, newDate, "Party founded date");
            existingParty.setState("pending");
            existingParty.setFoundedDate(party.getFoundedDate());
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Address Line 1
    public Party updateAddressLine1(String partyId, Address address) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            Address existingAddress = existingParty.getAddress();
            String previousAddress = existingAddress.getAddressLine_1();
            String newAddress = address.getAddressLine_1();
            historyService.addNewHistoryEntry(existingParty, previousAddress, newAddress, "Party headquarters address line 1");
            existingAddress.setAddressLine_1(newAddress);
            existingParty.setState("pending");
            addressRepository.save(existingAddress);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Address Line 2
    public Party updateAddressLine2(String partyId, Address address) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            Address existingAddress = existingParty.getAddress();
            String previousAddress = existingAddress.getAddressLine_2();
            String newAddress = address.getAddressLine_2();
            historyService.addNewHistoryEntry(existingParty, previousAddress, newAddress, "Party headquarters address line 2");
            existingAddress.setAddressLine_2(newAddress);
            existingParty.setState("pending");
            addressRepository.save(existingAddress);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update City
    public Party updateCity(String partyId, Address address) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            Address existingAddress = existingParty.getAddress();
            String previousCity = existingAddress.getCity();
            String newCity = address.getCity();
            historyService.addNewHistoryEntry(existingParty, previousCity, newCity, "Party headquarters address city");
            existingAddress.setCity(newCity);
            existingParty.setState("pending");
            addressRepository.save(existingAddress);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Postal Code
    public Party updatePostalCode(String partyId, Address address) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            Address existingAddress = existingParty.getAddress();
            String previousPostalCode = existingAddress.getPostalCode();
            String newPostalCode = address.getPostalCode();
            historyService.addNewHistoryEntry(existingParty, previousPostalCode, newPostalCode, "Party headquarters postal code");
            existingAddress.setPostalCode(newPostalCode);
            existingParty.setState("pending");
            addressRepository.save(existingAddress);
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    // Update Leader NIC
    public Party updateLeaderNic(String partyId, Party party) throws ClassNotFoundException {
        Optional<Party> existingPartyOptional = partyRepository.findById(Integer.valueOf(partyId));
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();
            String previousNic = existingParty.getLeaderId();
            String newNic = party.getLeaderId();
            historyService.addNewHistoryEntry(existingParty, previousNic, newNic, "Party leader");
            existingParty.setLeaderId(newNic);
            existingParty.setState("pending");
            return partyRepository.save(existingParty);
        } else {
            throw new ClassNotFoundException("Party not found");
        }
    }

    //update document
    public void updateDocument(MultipartFile file, String documentType, Integer partyId) throws IOException {
        // Verify if party exists
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found with ID: " + partyId));

        if (file != null) {
            try {
                // Delete existing document of the same type
                documentService.deleteDocument(party, documentType);
                // Save the new document
                Document newDocument = documentService.createAndSaveDocument(file, party);
            } catch (Exception e) {
                System.err.println("Error uploading the document: " + e.getMessage());
                throw new IOException("Error uploading the document: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("File is null");
        }
    }
}
