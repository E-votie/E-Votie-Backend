package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "https://e_votie.lahirujayathilake.me")
@RequestMapping("api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/url/{fileName}")
    ResponseEntity<?> getDocumentUrl(@PathVariable String fileName){
        try{
            String url = documentService.getDocumentUrl(fileName);
            return new ResponseEntity<String>(url, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error getting document url: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
