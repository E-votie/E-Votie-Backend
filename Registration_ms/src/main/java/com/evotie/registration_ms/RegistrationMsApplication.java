package com.evotie.registration_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegistrationMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationMsApplication.class, args);
    }

}