package com.evotie.fingerprint_ms.Service.FeignClients;

import com.evotie.fingerprint_ms.models.VoterHyperledgerFacric;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "HYPERLEDGERFABRICCLIENT-MS")
public interface HyperledgerFabricClient {

    @GetMapping("/api/HyperlegerFabric/getVoter")
    ResponseEntity<VoterHyperledgerFacric> getVoter(@RequestParam("voterID") String voterID);
}

