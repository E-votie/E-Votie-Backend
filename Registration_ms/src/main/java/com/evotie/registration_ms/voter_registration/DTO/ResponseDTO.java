package com.evotie.registration_ms.voter_registration.DTO;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private int status;
    private Object content;
    private String error;

    public ResponseDTO() {
    }

    public ResponseDTO(String message, int status, Object content, String error) {
        this.message = message;
        this.status = status;
        this.content = content;
        this.error = error;
    }
}
