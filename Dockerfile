FROM gradle:8.10-jdk21 AS builder
WORKDIR /app

# Копируем файлы проекта
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Используем GRADLE_CMD вместо ./gradlew
RUN gradle clean bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]