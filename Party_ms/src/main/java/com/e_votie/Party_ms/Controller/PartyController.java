package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Service.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    //new Party
    @PostMapping
    public ResponseEntity<?> createParty(@RequestBody Party party, @AuthenticationPrincipal Jwt jwt ) throws Exception {
        try{
            Party createdParty = partyService.createParty(party, jwt);
            return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
        }catch (Exception e){
            log.info(String.valueOf(e));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
