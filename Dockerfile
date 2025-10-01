# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Copy the application JAR file to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Command to run the application
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]
