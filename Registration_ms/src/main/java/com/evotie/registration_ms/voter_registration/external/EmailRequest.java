package com.evotie.registration_ms.voter_registration.external;

import lombok.Data;

import java.util.Map;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private boolean isHtml;
    private Map<String, String> headers;

    // Getters and setters
}
