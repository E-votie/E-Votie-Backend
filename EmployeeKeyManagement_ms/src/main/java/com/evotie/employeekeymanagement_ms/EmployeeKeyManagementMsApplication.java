package com.evotie.employeekeymanagement_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EmployeeKeyManagementMsApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.directory("EmployeeKeyManagement_ms/src/main/resources") // Specify the directory if needed
				.load();

		// Set environment variables as system properties
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(EmployeeKeyManagementMsApplication.class, args);
	}

}
