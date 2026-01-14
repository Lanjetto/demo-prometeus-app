FROM gradle:8.10-jdk21 AS builder
WORKDIR /app

COPY gradle gradle
COPY gradle.properties gradle.properties
COPY build.gradle settings.gradle ./
COPY src ./src

RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
