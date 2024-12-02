package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Service.PartyMemberService;
import com.e_votie.Party_ms.Service.VoterService;
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
@RequestMapping("api/party/member")
public class PartyMemberController {

    @Autowired
    private PartyMemberService partyMemberService;

    @Autowired
    private VoterService voterService;

    // Endpoint to register a new party member
    @PostMapping("/register")
    public ResponseEntity<PartyMember> registerPartyMember(@RequestParam String party, @RequestParam String voter, @RequestParam String role){
        try {
            PartyMember newPartyMember = partyMemberService.registerNewPartyMember(party, voter, role);
            return new ResponseEntity<>(newPartyMember , HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Endpoint to get party member by token
    @GetMapping
    public ResponseEntity<PartyMember> getPartyMemberByToken(@AuthenticationPrincipal Jwt jwt){
        try{
            return new ResponseEntity<>(partyMemberService.getPartyMemberByToken(jwt), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Endpoint to get party member by nic
    @GetMapping("/by/nic/{nic}")
    public ResponseEntity<PartyMember> getPartyMemberByNIC(@PathVariable("nic") String nic) {
        try{
            return new ResponseEntity<>(partyMemberService.getPartyMemberByNIC(nic), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Endpoint to get all party members of a party by party id
    @GetMapping("/all")
    public ResponseEntity<?> getPartyMembersByPartyId(@RequestParam("party") String partyId) {
        try{
            return new ResponseEntity<>(partyMemberService.getPartyMembersByPartyId(partyId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update a party member
    @PutMapping
    public ResponseEntity<PartyMember> updatePartyMember(@AuthenticationPrincipal Jwt jwt, @RequestBody PartyMember partyMember){
        try{
            return new ResponseEntity<>(partyMemberService.updatePartyMember(jwt, partyMember), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a party member
    @DeleteMapping
    public ResponseEntity<Void> removePartyMember(@RequestParam String nic) {
        try {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
