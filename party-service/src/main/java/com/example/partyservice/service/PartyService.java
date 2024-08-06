package com.example.partyservice.service;

import com.example.partyservice.model.Address;
import com.example.partyservice.model.Party;
import com.example.partyservice.repository.PartyRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    @Autowired
    private PartyRepostitory partyRepository;

    // Method to create a new Party
    public Party createParty(Party party) {
        return partyRepository.save(party);
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
    public Party updateParty(Party party) throws Exception {
        // Retrieve the existing party from the repository
        Optional<Party> existingPartyOptional = partyRepository.findById(party.getRegistrationId());

        // Check if the existing party is present
        if (existingPartyOptional.isPresent()) {
            Party existingParty = existingPartyOptional.get();

            // Update the fields of the existing party with values from the provided party
            // Update the fields of the existing party with values from the provided party
            existingParty.setPartyName(party.getPartyName());
            existingParty.setAbbreviation(party.getAbbreviation());
            existingParty.setFoundedDate(party.getFoundedDate());
            existingParty.setLeader(party.getLeader());
            existingParty.setAddressLine1(party.getAddressLine1());
            existingParty.setAddressLine2(party.getAddressLine2());
            existingParty.setCity(party.getCity());
            existingParty.setPostalCode(party.getPostalCode());
            existingParty.setContactNumber(party.getContactNumber());
            existingParty.setSymbol(party.getSymbol());
            existingParty.setPartyColors(party.getPartyColors());
            existingParty.setConstitution(party.getConstitution());
            existingParty.setFinancialStatements(party.getFinancialStatements());
            existingParty.setDeclaration(party.getDeclaration());
            existingParty.setStatus(party.getStatus());

            // Save the updated party back to the repository and return it
            return partyRepository.save(existingParty);

        } else {
            // Throw an exception if the party with the given ID is not found
            throw new Exception("Party with ID " + party.getRegistrationId() + " not found");
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