package com.e_votie.Party_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients
public class PartyMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartyMsApplication.class, args);
	}

}
