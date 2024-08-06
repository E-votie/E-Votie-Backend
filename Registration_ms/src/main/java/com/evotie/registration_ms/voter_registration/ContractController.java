package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.Service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestParam String userId, @RequestParam String walletAddress) {
        try {
            CompletableFuture<TransactionReceipt> future = contractService.addUser(userId, walletAddress);
            return ResponseEntity.ok("User addition initiated. Transaction hash: " + future.get().getTransactionHash());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user: " + e.getMessage());
        }
    }

    @GetMapping("/get-user-id")
    public ResponseEntity<String> getUserId(@RequestParam String walletAddress) {
        try {
            String userId = contractService.getUserId(walletAddress);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting user ID: " + e.getMessage());
        }
    }

    // Add similar endpoints for other contract methods
}