# Vytvoření obrazu gradle
FROM gradle:latest AS builder

# Nastavení pracovního adresáře
WORKDIR /app

# Kopírování souborů pro ziskání konfigurace
COPY . .

# Spuštění gradle pro vytvoření buildu
RUN gradle clean build -x test
# Vytvoření obrazu javy
FROM openjdk:21-slim-buster AS runtime

# Nastavení pracovního adresáře
WORKDIR /app

# Kopírování souborů pro spuštění aplikace
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Kopírování souborů pro ziskání konfigurace
COPY --from=builder /app/src/main/resources/application.yaml /app/resources/

# Spuštění aplikace
CMD ["java", "-jar", "/app/app.jar"]
