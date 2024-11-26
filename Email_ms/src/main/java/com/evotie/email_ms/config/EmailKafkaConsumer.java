package com.evotie.email_ms.config;

import com.evotie.email_ms.Model.Massage;
import com.evotie.email_ms.Model.EmailRequest;
import com.evotie.email_ms.URLReader;
import com.evotie.email_ms.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmailKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailKafkaConsumer.class);
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public EmailKafkaConsumer(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "email-requests", groupId = "email-group")
    public void listenEmailRequests(String message) throws Exception {
        log.info("Received email message: {}", message);
        EmailRequest emailRequest = objectMapper.readValue(message, EmailRequest.class);
        log.info(emailRequest.getBody());
        emailService.sendEmail(emailRequest);
    }

    @KafkaListener(topics = "massage-request", groupId = "massage-group")
    public void listenMassageRequests(String message) throws Exception {
        log.info("Received massage message: {}", message);
        Massage massageRequest = objectMapper.readValue(message, Massage.class);
        log.info(massageRequest.getBody());
        URLReader.sendMassage(massageRequest);
        // Implement massage request handling logic here
    }
}