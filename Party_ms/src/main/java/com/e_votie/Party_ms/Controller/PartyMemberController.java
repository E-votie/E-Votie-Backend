package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Service.PartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/party/member")
public class PartyMemberController {

    @Autowired
    private PartyMemberService partyMemberService;

    // Endpoint to register a new party member
    @PostMapping("/register")
    public ResponseEntity<PartyMember> registerPartyMember(@RequestParam String partyId, @RequestBody PartyMember partyMember){
        try {
            PartyMember newPartyMember = partyMemberService.registerNewPartyMember(partyId, partyMember);
            return new ResponseEntity<>(newPartyMember , HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Endpoint to get all party members of a given party
    @GetMapping("/all")
    public ResponseEntity<List<PartyMember>> getPartyMembersByPartyId(@RequestParam String partyId) throws Exception {
        List<PartyMember> partyMembers = partyMemberService.getPartyMembersByPartyId(partyId);
        return new ResponseEntity<>(partyMembers, HttpStatus.OK);
    }

    // Endpoint to get party member given member name
    @GetMapping("/search")
    public ResponseEntity<List<PartyMember>> getPartyMemberByMemberName(@RequestParam String name) throws Exception {
        List<PartyMember> partyMembers = partyMemberService.getPartyMemberByMemberName(name);
        return new ResponseEntity<>(partyMembers, HttpStatus.OK);
    }

    //Endpoint to get party member by nic
    @GetMapping
    public ResponseEntity<PartyMember> getPartyMemberByNIC(@RequestParam String nic){
        try{
            return new ResponseEntity<>(partyMemberService.getPartyMemberByNIC(nic), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update a party member
    @PutMapping
    public ResponseEntity<PartyMember> updatePartyMember(@RequestParam String nic, @RequestBody PartyMember partyMember){
        try{
            return new ResponseEntity<>(partyMemberService.updatePartyMember(nic, partyMember), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a party member
    @DeleteMapping
    public ResponseEntity<Void> removePartyMember(@RequestParam String nic){
        try{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
