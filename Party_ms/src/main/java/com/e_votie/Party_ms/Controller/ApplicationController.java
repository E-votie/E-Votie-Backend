package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@CrossOrigin(origins = "https://e_votie.lahirujayathilake.me")
@RequestMapping("api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    //Update party name
    @PutMapping("/update/party/{partyId}/name")
    public ResponseEntity<?> updatePartyName(@PathVariable String partyId, @RequestBody Party party) {
        try{
            return new ResponseEntity<>(applicationService.updatePartyName(partyId, party), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update abbreviation
    @PutMapping("/update/party/{partyId}/abbreviation")
    public ResponseEntity<?> updateAbbreviation(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateAbbreviation(partyId, party), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating abbreviation for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update founded date
    @PutMapping("/update/party/{partyId}/date")
    public ResponseEntity<?> updateFoundedDate(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateFoundedDate(partyId, party), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating founded date for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update Address Line 1
    @PutMapping("/update/party/{partyId}/address/line1")
    public ResponseEntity<?> updateAddressLine1(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateAddressLine1(partyId, party.getAddress()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating address line 1 for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update Address Line 2
    @PutMapping("/update/party/{partyId}/address/line2")
    public ResponseEntity<?> updateAddressLine2(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateAddressLine2(partyId, party.getAddress()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating address line 2 for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update city
    @PutMapping("/update/party/{partyId}/city")
    public ResponseEntity<?> updateCity(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateCity(partyId, party.getAddress()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating city for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update postal code
    @PutMapping("/update/party/{partyId}/postal")
    public ResponseEntity<?> updatePostalCode(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updatePostalCode(partyId, party.getAddress()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating postal code for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update leader NIC
    @PutMapping("/update/party/{partyId}/leader")
    public ResponseEntity<?> updateLeaderNic(@PathVariable String partyId, @RequestBody Party party) {
        try {
            return new ResponseEntity<>(applicationService.updateLeaderNic(partyId, party), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating leader NIC for partyId {}: {}", partyId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update documents
    @PostMapping("/document/upload")
    public ResponseEntity<String> updateDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam("partyId") String partyId) {
        try {
            applicationService.updateDocument(file, documentType, Integer.valueOf(partyId));
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
