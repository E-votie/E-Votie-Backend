package com.evotie.election_ms.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private String status;
    private Object data;

    public ResponseDTO(String message, String status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
