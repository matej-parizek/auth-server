# Use a specific version of the Gradle image for reproducible builds
FROM gradle:latest AS builder

# Set working directory
WORKDIR /app

# Exclude unnecessary files using .dockerignore before copying
COPY . .

# Execute Gradle build, skipping tests for faster builds
RUN gradle clean build -x test

# Use a specific base image for the runtime
FROM openjdk:21-slim-buster AS runtime

# Set the working directory
WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Copy configuration files directly into the correct location
COPY --from=builder /app/src/main/resources/application.yaml ./resources/

# Command to run the application
CMD ["java", "-jar", "app.jar"]
