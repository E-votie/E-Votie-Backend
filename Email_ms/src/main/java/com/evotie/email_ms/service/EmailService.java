package com.evotie.email_ms.service;

import com.evotie.email_ms.Model.Email;
import com.evotie.email_ms.Model.EmailRequest;
import com.evotie.email_ms.config.EmailKafkaProducer;
import com.evotie.email_ms.repo.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final EmailKafkaProducer kafkaProducer;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, EmailRepository emailRepository, EmailKafkaProducer kafkaProducer, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.kafkaProducer = kafkaProducer;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(EmailRequest request) throws Exception {
        log.info("Sending email to: {}", request.getTo());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.isHtml() ? generateEmailHtml(request.getTemplate(), request.getVariables()) : request.getBody(), request.isHtml());


        // Add custom headers
        if (request.getHeaders() != null) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                message.addHeader(entry.getKey(), entry.getValue());
            }
        }
        mailSender.send(message);

        // Save email to database
        Email email = new Email();
        email.setTo(request.getTo());
        email.setSubject(request.getSubject());
        email.setBody(request.getBody());
        email.setHtml(request.isHtml());
        email.setSentDate(new Date());
        emailRepository.save(email);
    }

    public String generateEmailHtml(String template, Map<String, Object> variables) {
        log.info("Generating email using template: {}", template);
        Context context = new Context();
        context.setVariables(variables);

        // Generate HTML using the template engine
        String emailHtml = templateEngine.process(template, context);
        log.info("Generated email HTML: {}", emailHtml);
        return emailHtml;
    }
}
