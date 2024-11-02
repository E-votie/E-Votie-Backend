package com.e_votie.Party_ms.Controller;

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
@RequestMapping("api/voter/")
public class VoterController {

    @Autowired
    private VoterService voterService;

    //Endpoint to verify the presence of a give voter
    @PostMapping("/verify/{nic}")
    private ResponseEntity<?> verifyVoter(@PathVariable String nic, @RequestHeader String token){
        try{
            ResponseEntity<?> voter = voterService.getMyDetails(token);
            return new ResponseEntity<>(voter, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
