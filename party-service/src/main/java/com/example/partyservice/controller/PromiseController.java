package com.example.partyservice.controller;

import com.example.partyservice.model.Promise;
import com.example.partyservice.service.PromiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manifesto/{manifestoId}")
public class PromiseController {

    @Autowired
    private PromiseService promiseService;

    // Endpoint to create a new Promise
    @PostMapping
    public ResponseEntity<Promise> createPromise(@PathVariable Integer manifestoId, @RequestBody Promise promise) {
        try {
            Promise createdPromise = promiseService.createPromise(manifestoId, promise);
            return ResponseEntity.ok(createdPromise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    // Endpoint to retrieve a Manifesto by its ID
    // Endpoint to retrieve all Manifestos
    // Endpoint to update an existing Manifesto
    // Endpoint to delete a Manifesto by its ID



}
