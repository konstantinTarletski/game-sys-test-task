#Prepare build environment
#FROM openjdk:11-jdk-slim AS build
FROM amazoncorretto:11-al2023-headless AS build
RUN apt-get update && apt-get install dos2unix
WORKDIR /app

# Download dependencies
COPY gradle /app/gradle
COPY gradlew /app
COPY build.gradle /app
RUN dos2unix gradlew && chmod +x gradlew && ./gradlew download

# Build artifact
COPY . /app
RUN dos2unix gradlew && chmod +x gradlew && ./gradlew build -x test

# Create minimal image from build artifact
#FROM openjdk:11-jdk-slim
FROM amazoncorretto:11-al2023-headless
COPY --from=build /app/build/libs/*.jar /app/
ENTRYPOINT ["java", "-jar", "/app/rss-reader-0.0.1-SNAPSHOT.jar"]
