package com.evotie.email_ms.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmailKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailStatus(String emailId, String status) {
        kafkaTemplate.send("registration", emailId, status);
    }
}
