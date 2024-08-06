package com.evotie.email_ms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class EmailRequest {
    @JsonProperty("to")
    private String to;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("body")
    private String body;

    private boolean isHtml;
    private Map<String, String> headers;

    // Getters and setters
}
