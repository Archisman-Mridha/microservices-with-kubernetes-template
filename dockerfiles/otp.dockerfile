#! building stage
FROM gradle:7-jdk19 as builder
WORKDIR /app

COPY ./microservices/otp .
RUN touch settings.gradle && \
    echo "rootProject.name = 'otp'" > settings.gradle

RUN gradle clean build

#! packaging stage
FROM openjdk:19-alpine as packager
WORKDIR /app

COPY --from=builder /app/build/libs/otp-0.0.1-SNAPSHOT.jar .

EXPOSE 4000
ENTRYPOINT [ "java", "-jar", "/app/otp-0.0.1-SNAPSHOT.jar" ]