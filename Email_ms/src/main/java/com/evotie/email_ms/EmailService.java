package com.evotie.email_ms;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final EmailKafkaProducer kafkaProducer;

    public EmailService(JavaMailSender mailSender, EmailRepository emailRepository, EmailKafkaProducer kafkaProducer) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void sendEmail(EmailRequest request) throws Exception {
        log.info("Sending email to: {}", request.getTo());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody(), request.isHtml());
        log.info("---------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // Add custom headers
        if (request.getHeaders() != null) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                message.addHeader(entry.getKey(), entry.getValue());
            }
        }
        log.info(message.getSubject());
        mailSender.send(message);
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // Save email to database
        Email email = new Email();
        email.setTo(request.getTo());
        email.setSubject(request.getSubject());
        email.setBody(request.getBody());
        email.setHtml(request.isHtml());
        email.setSentDate(new Date());
        log.info(email.toString());
        Email savedEmail = emailRepository.save(email);

//        // Send status update to Kafka
//        kafkaProducer.sendEmailStatus(savedEmail.getId().toString(), "SENT");
    }
}