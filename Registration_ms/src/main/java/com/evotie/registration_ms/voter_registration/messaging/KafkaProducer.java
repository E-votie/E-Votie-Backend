package com.evotie.registration_ms.voter_registration.messaging;

import com.evotie.registration_ms.voter_registration.external.EmailRequest;
import com.evotie.registration_ms.voter_registration.external.Massage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import brave.Tracer;
import brave.Span;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Tracer tracer;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, Tracer tracer) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    public void sendEmailRequest(EmailRequest emailRequest) throws Exception {
        log.info(emailRequest.toString());
        Span span = tracer.nextSpan().name("send-email-request");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span.start())) {
            span.tag("email.to", emailRequest.getTo());
            span.tag("email.subject", emailRequest.getSubject());

            String emailRequestJson = objectMapper.writeValueAsString(emailRequest);
            kafkaTemplate.send("email-requests", emailRequestJson);
        } finally {
            span.finish();
        }
    }

    public void sendMassageRequest(Massage massageRequest) throws Exception {
        log.info(massageRequest.toString());
        Span span = tracer.nextSpan().name("send-massage-request");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span.start())) {
            span.tag("massage.client", massageRequest.getTo());
            span.tag("massage.body", massageRequest.getBody());

            String massageRequestJson = objectMapper.writeValueAsString(massageRequest);
            kafkaTemplate.send("massage-request", massageRequestJson);
        } finally {
            span.finish();
        }
    }
}
