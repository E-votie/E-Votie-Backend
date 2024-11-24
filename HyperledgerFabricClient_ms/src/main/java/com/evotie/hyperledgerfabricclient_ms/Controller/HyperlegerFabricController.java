package com.evotie.hyperledgerfabricclient_ms.Controller;


import com.evotie.hyperledgerfabricclient_ms.Service.Hyperlegerfabric_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/HyperlegerFabric")
public class HyperlegerFabricController {

    @Autowired
    private Hyperlegerfabric_Service hyperlegerfabricService;

    @PostMapping("/createVoter")
    public ResponseEntity<String> createVoter(String NIC, String Name, String voterID, String BiometricTemplate) {
        return hyperlegerfabricService.createVoter(NIC, Name, voterID, BiometricTemplate);
    }

    @GetMapping("/getVoter")
    public ResponseEntity<?> createVoter(@RequestParam String voterID) {
        return hyperlegerfabricService.getVoter(voterID);
    }

    @PostMapping("/assignVoterToElection")
    public ResponseEntity<String> assignVoterToElection(String voterID, String electionID, boolean eligibility, String pollingStation){
        return hyperlegerfabricService.assignVoterToElection(voterID, electionID, eligibility, pollingStation);
    }

    @PostMapping("/createElection")
    public ResponseEntity<String> createElection(String contractAddress, String electionID, String endTime, String startTime){
        return hyperlegerfabricService.CreateElection(contractAddress, electionID, endTime, startTime);
    }

    @GetMapping("/recordVote")
    public ResponseEntity<String> recordVote(String voterID, String electionID){
        return hyperlegerfabricService.recordVote(voterID, electionID);
    }

    @GetMapping("/getElection")
    public ResponseEntity<String> getElection(String electionID){
        return hyperlegerfabricService.getElection(electionID);
    }

    @GetMapping("/getVoterElectionRelation")
    public ResponseEntity<String> getVoterElectionRelation(String voterID, String electionID){
        return hyperlegerfabricService.getVoterElectionRelation(voterID, electionID);
    }

}
