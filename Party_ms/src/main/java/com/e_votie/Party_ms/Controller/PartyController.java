package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Service.FileMsClient;
import com.e_votie.Party_ms.Service.PartyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    //create new party
    @PostMapping
    public ResponseEntity<?> createParty(
            @RequestPart("party") String partyJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal Jwt jwt) {
        try {
            // Deserialize JSON to Party object
            ObjectMapper objectMapper = new ObjectMapper();
            Party party = objectMapper.readValue(partyJson, Party.class);

            // Create the Party and handle file uploads
            Party createdParty = partyService.createParty(party, files, jwt);

            // Return the created Party as the response
            return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating party: ", e);
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
    @GetMapping
    public ResponseEntity<Optional<Party>> getPartyByName(@RequestParam String partyName){
        Optional<Party> party = partyService.getPartyByName(partyName);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }


    //get all parties
    @GetMapping("/all")
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
