package com.evotie.registration_ms.voter_registration.Config;

import com.evotie.registration_ms.voter_registration.VoterRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final VoterRegistrationService voterRegistrationService;

    @Autowired
    public WebSocketConfig(VoterRegistrationService voterRegistrationService) {
        this.voterRegistrationService = voterRegistrationService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(fingerprintHandler(), "/fingerprint-websocket")
                .setAllowedOrigins("*"); // Be cautious with this in production
    }

    @Bean
    public FingerprintWebSocketHandler fingerprintHandler() {
        return new FingerprintWebSocketHandler(voterRegistrationService);
    }
}