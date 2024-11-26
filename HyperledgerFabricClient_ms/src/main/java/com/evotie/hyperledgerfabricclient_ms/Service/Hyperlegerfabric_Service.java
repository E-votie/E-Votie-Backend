package com.evotie.hyperledgerfabricclient_ms.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class Hyperlegerfabric_Service {

    @Value("${HyperlegerFabric_URL}")
    private String hyperlegerFabricUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity<String> createVoter(String NIC, String Name, String voterID, String BiometricTemplate) {
        String url = hyperlegerFabricUrl + "/invoke/CreateVoter";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("nic", NIC);
        inputNode.put("name", Name);
        inputNode.put("voterID", voterID);
        inputNode.put("biometricTemplate", BiometricTemplate);
        log.info("Create Voter: " + inputNode.toString());

        return sendPostRequest(url, inputNode);
    }

    public ResponseEntity<?> getVoter(String voterID) {
        String url = hyperlegerFabricUrl + "/query/GetVoter";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("voterID", voterID);
        ResponseEntity<?> response = sendPostRequest(url, inputNode);
        log.info("Get Voter: " + response.getBody());
        return response;
    }

    public ResponseEntity<String> assignVoterToElection(String voterID, String electionID, boolean eligibility, String pollingStation) {
        String url = hyperlegerFabricUrl + "/invoke/AssignVoterToElection";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("voterID", voterID);
        inputNode.put("electionID", electionID);
        inputNode.put("eligibility", eligibility);
        inputNode.put("pollingStation", pollingStation);

        return sendPostRequest(url, inputNode);
    }

    public ResponseEntity<String> CreateElection(String contractAddress,String electionID,String endTime,String startTime) {
        String url = hyperlegerFabricUrl + "/invoke/CreateElection";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("contractAddress", contractAddress);
        inputNode.put("electionID", electionID);
        inputNode.put("endTime", endTime);
        inputNode.put("startTime", startTime);

        return sendPostRequest(url, inputNode);
    }

    public ResponseEntity<String> recordVote(String electionID, String voterID) {
        String url = hyperlegerFabricUrl + "/invoke/RecordVote";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("electionID", electionID);
        inputNode.put("voterID", voterID);

        return sendPostRequest(url, inputNode);
    }

    public ResponseEntity<String> getElection(String electionID) {
        String url = hyperlegerFabricUrl + "/query/GetElection";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("electionID", electionID);

        return sendPostRequest(url, inputNode);
    }

    public ResponseEntity<String> getVoterElectionRelation(String electionID,String voterID) {
        String url = hyperlegerFabricUrl + "/query/GetVoterElectionRelation";
        ObjectNode inputNode = mapper.createObjectNode();
        inputNode.put("electionID", electionID);
        inputNode.put("voterID", voterID);

        return sendPostRequest(url, inputNode);
    }

    private ResponseEntity<String> sendPostRequest(String url, ObjectNode inputNode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.set("input", inputNode);

        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error creating JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }
}
