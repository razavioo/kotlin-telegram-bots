# Use the official Gradle image as a base image
FROM gradle:jdk11 AS build

# Set the working directory in the image
WORKDIR /app

# Copy the Gradle files first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts /app/

# Copy the rest of the application
COPY src /app/src

# Build the application
RUN gradle build --no-daemon

# Start with a fresh JDK 11 image for smaller image size
FROM openjdk:11-jre-slim

# Set the working directory in the image
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/*.jar /app/namebot.jar

# Set environment variable for the bot token (replace with your own if needed)
ENV BOT_TOKEN your_bot_token_here

# Command to run the application
CMD ["java", "-jar", "/app/namebot.jar"]
