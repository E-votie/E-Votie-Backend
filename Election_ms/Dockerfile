# Use an official OpenJDK image to build the Spring Boot app
FROM openjdk:17-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy the compiled jar file into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app is running on
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
