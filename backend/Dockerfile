FROM gradle:7.6-jdk11 AS build

WORKDIR /app
COPY . .

RUN gradle clean build -x test

FROM openjdk:11-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/backend-1.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
