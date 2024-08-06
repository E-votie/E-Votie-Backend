package com.evotie.registration_ms.voter_registration.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class VoterService_Hyperlegerfabric {
    public ResponseEntity<String> createVoter(String NIC, String Name, String voterID, byte[] BiometricTemplate) {
        String URL = "http://35.219.137.228:5000/api/v1/namespaces/default/apis/e_votie/invoke/CreateVoter";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        ObjectNode inputNode = rootNode.putObject("input");
        inputNode.put("nic", NIC);
        inputNode.put("name", Name);
        inputNode.put("voterID", voterID);
        inputNode.put("biometricTemplate", BiometricTemplate);

        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error creating JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(URL, request, String.class);
    }
}