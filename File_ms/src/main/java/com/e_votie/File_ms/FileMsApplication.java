package com.e_votie.File_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class FileMsApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("File_ms/src/main/resources") // Specify the directory if needed
				.load();

		// Set environment variables as system properties
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(FileMsApplication.class, args);
	}

}
