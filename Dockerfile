FROM ubuntu:latest
LABEL authors="Levis Nyingi"
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# ENV PORT=8080
EXPOSE 8080
