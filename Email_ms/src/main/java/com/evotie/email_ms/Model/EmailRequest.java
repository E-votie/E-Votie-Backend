package com.evotie.email_ms.Model;

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

    @JsonProperty("template")
    private String template;

    private boolean isHtml;
    private Map<String, String> headers;
    private Map<String, Object> variables;

    // Getters and setters
}
