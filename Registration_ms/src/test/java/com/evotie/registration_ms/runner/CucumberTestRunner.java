package com.evotie.registration_ms.runner;

import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Correct feature file path
        glue = "com.evotie.registration_ms.steps", // Package where step definitions reside
        plugin = {"pretty", "html:target/cucumber-reports"}, // Reports for test execution
        monochrome = true // Makes console output readable
)
@CucumberContextConfiguration
@SpringBootTest
public class CucumberTestRunner {
}
