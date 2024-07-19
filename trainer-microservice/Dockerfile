# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/trainer-microservice-0.0.1-SNAPSHOT.jar /app/

# Expose the port the app runs on
EXPOSE 8081

# Define the command to run the app
CMD ["java", "-jar", "trainer-microservice-0.0.1-SNAPSHOT.jar"]