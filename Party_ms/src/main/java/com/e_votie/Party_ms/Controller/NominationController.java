package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Service.NominationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "https://e_votie.lahirujayathilake.me")
@RequestMapping("api/nomination")
public class NominationController {

    @Autowired
    private NominationService nominationService;

    @GetMapping("/elections")
    private ResponseEntity<?> getAllElections(@AuthenticationPrincipal Jwt jwt){
        try{
            return new ResponseEntity<>(nominationService.getAllElections(jwt), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
