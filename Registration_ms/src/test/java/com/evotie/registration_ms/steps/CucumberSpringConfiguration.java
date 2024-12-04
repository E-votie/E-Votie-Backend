package com.evotie.registration_ms.steps;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = com.evotie.registration_ms.steps.CucumberSpringConfiguration.class)
public class CucumberSpringConfiguration {
    // This class is required for Spring context initialization in Cucumber
}

