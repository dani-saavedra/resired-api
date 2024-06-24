FROM gradle:8.8.0-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/

RUN gradle build --no-daemon || return 0

COPY src /app/src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=resired
ENV DB_USER=user
ENV DB_PASS=password

WORKDIR /app

COPY --from=build /app/build/libs/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]
