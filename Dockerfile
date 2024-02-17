
LABEL authors="Levis Nyingi"
FROM eclipse-temurin:17-jdk As build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080