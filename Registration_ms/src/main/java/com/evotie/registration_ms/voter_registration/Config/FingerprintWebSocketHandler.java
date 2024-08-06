package com.evotie.registration_ms.voter_registration.Config;

import com.evotie.registration_ms.voter_registration.VoterRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class FingerprintWebSocketHandler extends TextWebSocketHandler {


    private final VoterRegistrationService voterRegistrationService;

    public FingerprintWebSocketHandler(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
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

        log.info("Received message from device {}: {}", sourceDevice, content);
        if("SCAN".equalsIgnoreCase(content)) {
            sendScanCommand(targetDevice, sourceDevice, "SCAN");
        }
        // Process the message based on its content
        if ("SCAN_COMPLETE".equals(content)) {
            List<Integer> templateData = (List<Integer>) jsonMessage.get("templateData");
            byte[] compressed = compressList(templateData);
            voterRegistrationService.AddVoter(targetDevice, compressed );
            sendScanCommand(targetDevice, sourceDevice, "SCAN_COMPLETE");
        } else if (content.startsWith("MATCH:")) {
            String confidenceScore = content.split(":")[1];
            log.info("Match found for device {} with confidence: {}", sourceDevice, confidenceScore);
        }else{
            sendScanCommand(targetDevice, sourceDevice, content);
        }
        // Add more message handlers as needed
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        ByteBuffer buffer = message.getPayload();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        try {
            Map<String, Object> jsonMessage = objectMapper.readValue(data, Map.class);
            String sourceDevice = (String) jsonMessage.get("sourceDevice");
            byte[] templateData = (byte[]) jsonMessage.get("templateData");

            log.info("Received template data from device: {}, size: {}", sourceDevice, templateData.length);

            // Process the template data
            processTemplateData(sourceDevice, templateData);
        } catch (IOException e) {
            log.error("Error parsing binary message", e);
        }
    }

    private void processTemplateData(String deviceId, byte[] templateData) {
        // Here you can process the template data
        // For example, you might want to save it to a database
        log.info("Processing template data for device: {}, length: {}", deviceId, templateData.length);
        // Add your processing logic here
    }

    public void sendScanCommand(String deviceId, String SourceID, String message) throws IOException {
        WebSocketSession session = deviceSessions.get(deviceId);
        if (session != null && session.isOpen()) {
            Map<String, Object> jsonMessage = Map.of(
                    "targetDevice", deviceId,
                    "sourceDevice", SourceID,
                    "message", message
            );
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(jsonMessage)));
        } else {
            log.error("Device not connected: {}", deviceId);
        }
    }

    public void sendMatchCommand(String deviceId) throws IOException {
        WebSocketSession session = deviceSessions.get(deviceId);
        if (session != null && session.isOpen()) {
            Map<String, Object> jsonMessage = Map.of(
                    "targetDevice", deviceId,
                    "sourceDevice", "SERVER",
                    "message", "MATCH"
            );
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(jsonMessage)));
        } else {
            log.error("Device not connected: {}", deviceId);
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

    public static byte[] compressList(List<Integer> list) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(list.size());  // Write the size of the list

            int prev = 0;
            for (int num : list) {
                int delta = num - prev;
                writeVarInt(dos, delta);
                prev = num;
            }

            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    private static void writeVarInt(DataOutputStream dos, int value) throws IOException {
        while ((value & 0xFFFFFF80) != 0) {
            dos.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        dos.writeByte(value & 0x7F);
    }

    public static List<Integer> decompressList(byte[] compressed) {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
        DataInputStream dis = new DataInputStream(bais);
        List<Integer> result = new ArrayList<>();

        try {
            int size = dis.readInt();  // Read the size of the list

            int prev = 0;
            for (int i = 0; i < size; i++) {
                int delta = readVarInt(dis);
                int num = prev + delta;
                result.add(num);
                prev = num;
            }

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static int readVarInt(DataInputStream dis) throws IOException {
        int value = 0;
        int shift = 0;
        byte b;

        do {
            b = dis.readByte();
            value |= (b & 0x7F) << shift;
            shift += 7;
        } while ((b & 0x80) != 0);

        return value;
    }
}