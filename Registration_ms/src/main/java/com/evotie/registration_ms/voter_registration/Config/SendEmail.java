package com.evotie.registration_ms.voter_registration.Config;

import com.evotie.registration_ms.voter_registration.external.EmailRequest;
import com.evotie.registration_ms.voter_registration.messaging.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmail {

    @Autowired
    private KafkaProducer kafkaProducer;

    private void sendEmail(String email, String subject, String message, boolean isHtml, String template, Map<String, Object> variables) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject(subject);
        emailRequest.setBody(message);
        emailRequest.setTemplate(template);
        emailRequest.setVariables(variables);
        emailRequest.setHtml(isHtml);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Priority", "1");
        emailRequest.setHeaders(headers);

        // Send email request
        kafkaProducer.sendEmailRequest(emailRequest);
    }

    public void triggerSendEmail(String email, String subject, String message, boolean isHtml, String template, Map<String, Object> variables) throws Exception {
        sendEmail(email, subject, message, isHtml, template, variables);  // Call the private method internally
    }
}
