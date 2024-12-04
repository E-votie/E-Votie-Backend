package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.Inquiry;
import com.e_votie.Party_ms.Service.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "https://e_votie.lahirujayathilake.me")
@RequestMapping("api/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping("/submit")
    public ResponseEntity<Inquiry> submitInquiry(@AuthenticationPrincipal Jwt jwt, @RequestBody Inquiry inquiry) {
        Inquiry createdInquiry = inquiryService.createInquiry(jwt, inquiry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInquiry);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<Inquiry>> getInquiriesByPartyId(
            @PathVariable String partyId
    ) {
        List<Inquiry> inquiries = inquiryService.getInquiriesByPartyId(partyId);
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Inquiry>> getInquiryById(@PathVariable String id) {
        Optional<Inquiry> inquiry = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(inquiry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable String id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build();
    }
}
