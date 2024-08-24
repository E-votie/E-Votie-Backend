package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    //new Party
    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody Party party, @AuthenticationPrincipal OAuth2ResourceServerProperties.Jwt jwt ) throws Exception {
        String userId = jwt.getClaimAsString("sub");

        Party createdParty = partyService.createParty(party);
        return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
    }

    //get Party by party Id
    @GetMapping("/{partyId}")
    public ResponseEntity<Optional<Party>> getPartyById(@PathVariable String partyId){
        Optional<Party> party = partyService.getPartyById(partyId);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }


    //get party by party name
    @GetMapping("/all")
    public ResponseEntity<Optional<Party>> getPartyByName(@RequestParam String partyName){
        Optional<Party> party = partyService.getPartyByName(partyName);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }


    //get all parties
    @GetMapping
    public ResponseEntity<List<Party>> getAllParties(){
        List<Party> allParties = partyService.getAllParties();
        return new ResponseEntity<>(allParties, HttpStatus.OK);
    }


    //update Party
    @PutMapping
    public ResponseEntity<Party> updateParty(@RequestBody Party party) throws Exception {
        Party updatedParty = partyService.updateParty(party);
        return new ResponseEntity<>(updatedParty ,HttpStatus.OK);
    }


    //delete Party
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Integer id) {
        try {
            partyService.deleteParty(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
