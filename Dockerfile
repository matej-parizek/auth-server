FROM openjdk:21-jdk-slim
COPY build/libs/*-all.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
