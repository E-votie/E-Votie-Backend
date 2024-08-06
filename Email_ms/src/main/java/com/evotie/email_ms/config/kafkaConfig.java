package com.evotie.email_ms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaConfig {

    @Bean
    public NewTopic emailRequests() {
        return TopicBuilder.name("email-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic massageRequests() {
        return TopicBuilder.name("massage-request")
                .partitions(1)
                .replicas(1)
                .build();
    }
}