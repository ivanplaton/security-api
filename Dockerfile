# Use an official OpenJDK 21 runtime as a base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built Spring Boot jar file into the container
COPY build/libs/security-0.0.1-SNAPSHOT.jar /app/backend-security.jar

# Expose the port your Spring Boot app will run on
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "backend-security.jar"]
