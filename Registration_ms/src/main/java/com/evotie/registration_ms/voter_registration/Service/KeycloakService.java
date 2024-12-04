package com.evotie.registration_ms.voter_registration.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;
    private final String realm = "demo";
    private final String clientId = "demo-rest-api";

    private String getToken(){
        log.info("------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++++++++++");
        log.info("Getting token");
        RestTemplate restTemplate = new RestTemplate();

        // Get access token
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "password");
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", "admin");
        tokenBody.add("password", "1111");

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenBody, tokenHeaders);

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                keycloakUrl + "/realms/demo/protocol/openid-connect/token",
                tokenRequest,
                Map.class
        );

        return((String) tokenResponse.getBody().get("access_token"));
    }

    public String createUser(String username, String email, String firstName, String lastName, String password) {

        RestTemplate restTemplate = new RestTemplate();
        String accessToken = getToken();
        log.info("---------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>p-----------------");
        log.info(accessToken);
        // Create user
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> userRequest = new HashMap<>();
        userRequest.put("username", username);
        userRequest.put("email", email);
        userRequest.put("firstName", firstName);
        userRequest.put("lastName", lastName);
        userRequest.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", password);
        credentials.put("temporary", true);

        userRequest.put("credentials", Collections.singletonList(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                keycloakUrl+"/admin/realms/demo/users",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            String locationHeader = response.getHeaders().getFirst("Location");
            if (locationHeader != null) {
                String[] parts = locationHeader.split("/");
                String userId = parts[parts.length - 1];
                log.info("User created successfully. User ID: " + userId);
                return userId;
            } else {
                throw new RuntimeException("User created but ID not found in response");
            }
        } else {
            throw new RuntimeException("Failed to create user: " + response.getBody());
        }
    }

    public void assignRoleToUser(String userId, String roleName) {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // First, get all client roles
        ResponseEntity<List<Map<String, Object>>> rolesResponse = restTemplate.exchange(
                keycloakUrl+"/admin/realms/demo/clients/162f9e0e-64e2-4ae7-84fe-93d625a161bd/roles",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> roles = rolesResponse.getBody();
        log.info(roles.toString());
        Map<String, Object> targetRole = null;
        for (Map<String, Object> role : roles) {
            if (roleName.equals(role.get("name"))) {
                targetRole = role;
                break;
            }
        }

        if (targetRole == null) {
            throw new RuntimeException("Client role not found: " + roleName);
        }

        // Prepare the role assignment request
        List<Map<String, Object>> rolesToAssign = Collections.singletonList(targetRole);

        HttpEntity<List<Map<String, Object>>> requestEntity = new HttpEntity<>(rolesToAssign, headers);

        // Assign the client role to the user
        ResponseEntity<Void> response = restTemplate.exchange(
                keycloakUrl + "/admin/realms/demo/users/" + userId + "/role-mappings/clients/162f9e0e-64e2-4ae7-84fe-93d625a161bd",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("Client role assigned successfully");
        } else {
            log.info("Failed to assign client role: " + response.getStatusCode());
        }
    }
}