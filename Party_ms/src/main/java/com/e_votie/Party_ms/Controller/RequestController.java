package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Request;
import com.e_votie.Party_ms.Service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/request")
public class RequestController {
    @Autowired
    private RequestService requestService;

    //create a new party
    @PostMapping("/new")
    public ResponseEntity<?> createNewRequest(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody Request request
    ) {
        try {
            return ResponseEntity.ok(requestService.createNewRequest(jwt, request));
        } catch (Exception e) {
            log.error("Error creating request", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get all parties by party registration id
    @GetMapping("/party/{partyRegistrationId}")
    public ResponseEntity<?> getRequestsByPartyId(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Integer partyRegistrationId
    ) {
        try {
            return ResponseEntity.ok(requestService.getRequestsByPartyId(jwt, partyRegistrationId));
        } catch (Exception e) {
            log.error("Error fetching requests for party", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get all party requests by party member nic
    @GetMapping("/receiver/{receiverNic}")
    public ResponseEntity<?> getRequestsByReceiverNic(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String receiverNic
    ) {
        try {
            return ResponseEntity.ok(requestService.getRequestsByReceiverNic(jwt, receiverNic));
        } catch (Exception e) {
            log.error("Error fetching requests by receiver", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get request by party and receiver nic
    @GetMapping("party/{partyRegistrationId}/receiver/{receiverNic}")
    public ResponseEntity<?> getRequestsByPartyAndReceiverNic(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String partyRegistrationId,
            @PathVariable String receiverNic
    ) {
        try {
            return ResponseEntity.ok(requestService.getRequestsByPartyAndReceiverNic(jwt, partyRegistrationId, receiverNic));
        } catch (Exception e) {
            log.error("Error fetching requests by receiver", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //update the staus of a given request
    @PutMapping("/status/{requestId}")
    public ResponseEntity<?> updateRequestStatus(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Integer requestId,
            @RequestBody String status
    ) {
        try {
            return ResponseEntity.ok(requestService.updateRequestStatus(jwt, requestId, status));
        } catch (Exception e) {
            log.error("Error updating request status", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}