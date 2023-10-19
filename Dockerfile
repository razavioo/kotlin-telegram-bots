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
