FROM openjdk:11-jdk-slim AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew clean build -x test

FROM openjdk:11-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
