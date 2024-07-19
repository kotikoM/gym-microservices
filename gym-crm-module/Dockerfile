# Use an official OpenJDK 21 runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/gym-crm-module-0.0.1-SNAPSHOT.jar /app/

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the app
CMD ["java", "-jar", "gym-crm-module-0.0.1-SNAPSHOT.jar"]
