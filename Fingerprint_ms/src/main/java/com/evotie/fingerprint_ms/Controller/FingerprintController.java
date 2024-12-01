package com.evotie.fingerprint_ms.Controller;//package com.evotie.registration_ms.voter_registration;
//
//import com.evotie.registration_ms.voter_registration.Config.FingerprintWebSocketHandler;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/fingerprint")
//public class FingerprintController {
//
//    private final FingerprintWebSocketHandler fingerprintHandler;
//
//    public FingerprintController(FingerprintWebSocketHandler fingerprintHandler) {
//        this.fingerprintHandler = fingerprintHandler;
//    }
//
//    @PostMapping("/scan/{deviceId}")
//    public ResponseEntity<?> initiateScanner(@PathVariable String deviceId) {
//        try {
//            fingerprintHandler.sendScanCommand(deviceId, );
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send scan command");
//        }
//    }
//
//    @PostMapping("/match/{deviceId}")
//    public ResponseEntity<?> initiateMatch(@PathVariable String deviceId) {
//        try {
//            fingerprintHandler.sendMatchCommand(deviceId);
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send match command");
//        }
//    }
//
//    @PostMapping("/template/{deviceId}")
//    public ResponseEntity<?> sendTemplate(@PathVariable String deviceId, @RequestBody byte[] templateData) {
//        try {
//            fingerprintHandler.sendTemplateData(deviceId, templateData);
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send template data");
//        }
//    }
//}