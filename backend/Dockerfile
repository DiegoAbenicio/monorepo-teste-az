FROM openjdk:11-jdk-slim

WORKDIR /app

COPY build/libs/backend-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
