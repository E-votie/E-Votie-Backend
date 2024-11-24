package com.evotie.registration_ms.voter_registration.Config;

import com.evotie.registration_ms.voter_registration.external.Massage;
import com.evotie.registration_ms.voter_registration.messaging.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {

    @Autowired
    private KafkaProducer kafkaProducer;

    private void sendMessage(String Contact, String subject, String message) throws Exception {
        Massage messageRequest = new Massage();
        messageRequest.setTo(Contact);
        messageRequest.setSubject(subject);
        messageRequest.setBody(message);

        // Send email request
        kafkaProducer.sendMassageRequest(messageRequest);
    }

    public void triggerSendMessage(String Contact, String subject, String message) throws Exception {
        sendMessage(Contact, subject, message);  // Call the private method internally
    }
}
