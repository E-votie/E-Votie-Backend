package com.evotie.fingerprint_ms.Config;

import com.evotie.fingerprint_ms.Service.FeignClients.HyperledgerFabricClient;
import com.evotie.fingerprint_ms.Service.FeignClients.VoterRegistrationFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final VoterRegistrationFeignClient voterRegistrationFeignClient;
    private final HyperledgerFabricClient hyperledgerFabricClient;

    public WebSocketConfig(VoterRegistrationFeignClient voterRegistrationFeignClient, HyperledgerFabricClient hyperledgerFabricClient) {
        this.voterRegistrationFeignClient = voterRegistrationFeignClient;
        this.hyperledgerFabricClient = hyperledgerFabricClient;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(fingerprintHandler(), "/fingerprint-websocket")
                .setAllowedOrigins("*"); // Be cautious with this in production
    }

    @Bean
    public FingerprintWebSocketHandler fingerprintHandler() {
        return new FingerprintWebSocketHandler(voterRegistrationFeignClient, hyperledgerFabricClient);
    }
}