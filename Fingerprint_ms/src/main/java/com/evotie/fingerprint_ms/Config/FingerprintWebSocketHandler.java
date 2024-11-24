package com.evotie.fingerprint_ms.Config;

import com.evotie.fingerprint_ms.Service.FeignClients.HyperledgerFabricClient;
import com.evotie.fingerprint_ms.Service.FeignClients.VoterRegistrationFeignClient;
import com.evotie.fingerprint_ms.models.Fingerprint;
import com.evotie.fingerprint_ms.models.VoterHyperledgerFacric;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class FingerprintWebSocketHandler extends TextWebSocketHandler {

    private final VoterRegistrationFeignClient voterRegistrationFeignClient;
    private final HyperledgerFabricClient hyperledgerFabricClient;

    public FingerprintWebSocketHandler(VoterRegistrationFeignClient voterRegistrationFeignClient, HyperledgerFabricClient hyperledgerFabricClient) {
        this.voterRegistrationFeignClient = voterRegistrationFeignClient;
        this.hyperledgerFabricClient = hyperledgerFabricClient;
    }

    private final Map<String, WebSocketSession> deviceSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String deviceId = extractDeviceId(session);
        deviceSessions.put(deviceId, session);
        log.info("New connection established for device: {}", deviceId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String deviceId = extractDeviceId(session);
        deviceSessions.remove(deviceId);
        log.info("Connection closed for device: {}", deviceId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> jsonMessage = objectMapper.readValue(payload, Map.class);

        String sourceDevice = (String) jsonMessage.get("sourceDevice");
        String targetDevice = (String) jsonMessage.get("targetDevice");
        String content = (String) jsonMessage.get("message");
        String applicationId = (String) jsonMessage.get("applicationId");

        log.info("Received message from device {}: {} to {}", sourceDevice, content, targetDevice);
        if("SCAN".equalsIgnoreCase(content)) {
            sendScanCommand(targetDevice, sourceDevice, "SCAN", applicationId);
        }
        // Process the message based on its content
        if ("SCAN_COMPLETE".equalsIgnoreCase(content)) {
            log.info(jsonMessage.toString());
            String templateData = convertListToString((List<List<Integer>>) jsonMessage.get("templateData"));
            log.info("Received template data from device {}: {}", sourceDevice, templateData);
            sendScanCommand(targetDevice, sourceDevice, "SCAN_COMPLETE", applicationId);
            sendScanCommand(sourceDevice, targetDevice, "RESET", applicationId);
            ResponseEntity<?> response = voterRegistrationFeignClient.AddFingerprint(new Fingerprint(applicationId, templateData));
            log.info("Response from voter registration service: {}", response);
            log.info("Sending scan complete command to device {}", targetDevice);
        } else if ("MATCH".equalsIgnoreCase(content)) {
            VoterHyperledgerFacric voter = hyperledgerFabricClient.getVoter(applicationId).getBody();
            sendMatchCommand(targetDevice, sourceDevice, "MATCH", applicationId, voter.getBiometricTemplate());
            log.info(voter.toString());
        }else{
            sendScanCommand(targetDevice, sourceDevice, content, applicationId);
        }
        // Add more message handlers as needed
    }

    public void sendScanCommand(String deviceId, String SourceID, String message, String applicationId) throws IOException {
        WebSocketSession session = deviceSessions.get(deviceId);
        if (session != null && session.isOpen()) {
            Map<String, Object> jsonMessage = Map.of(
                    "targetDevice", deviceId,
                    "sourceDevice", SourceID,
                    "message", message,
                    "applicationId", applicationId
            );
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(jsonMessage)));
        } else {
            log.error("Device not connected: {}", deviceId);
        }
    }

    public void sendMatchCommand(String targetDevice, String sourceDevice, String content, String applicationId, String biometric) throws IOException {
        WebSocketSession session = deviceSessions.get(targetDevice);
        if (session != null && session.isOpen()) {
            Map<String, Object> MatchjsonMessage = Map.of(
                    "targetDevice", targetDevice,
                    "sourceDevice", sourceDevice,
                    "message", content,
                    "applicationId", applicationId,
                    "templateData", convertStringToList(biometric)
            );
            log.info(MatchjsonMessage.toString());
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(MatchjsonMessage)));
        } else {
            log.error("Device not connected: {}", targetDevice);
        }
    }

    public void sendTemplateData(String deviceId, byte[] templateData) throws IOException {
        WebSocketSession session = deviceSessions.get(deviceId);
        if (session != null && session.isOpen()) {
            Map<String, Object> jsonMessage = Map.of(
                    "targetDevice", deviceId,
                    "sourceDevice", "SERVER",
                    "templateData", templateData
            );
            byte[] jsonBytes = objectMapper.writeValueAsBytes(jsonMessage);
            session.sendMessage(new BinaryMessage(ByteBuffer.wrap(jsonBytes)));
        } else {
            log.error("Device not connected: {}", deviceId);
        }
    }

    private String extractDeviceId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("id=")) {
            return query.substring(3);
        }
        return "UNKNOWN";
    }

    public static String convertListToString(List<List<Integer>> templateData) {
        StringBuilder result = new StringBuilder();

        for (List<Integer> pair : templateData) {
            // Convert the pair to a string "a,b"
            result.append(pair.get(0)).append(",").append(pair.get(1)).append("|");
        }

        // Remove the trailing "|"
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }

    public static List<List<Integer>> convertStringToList(String data) {
        List<List<Integer>> result = new ArrayList<>();

        // Split the string by "|"
        String[] pairs = data.split("\\|");

        for (String pair : pairs) {
            // Split each pair by ","
            String[] values = pair.split(",");

            // Convert the values to integers and add them to the list
            List<Integer> innerList = new ArrayList<>();
            innerList.add(Integer.parseInt(values[0]));
            innerList.add(Integer.parseInt(values[1]));

            result.add(innerList);
        }

        return result;
    }
}