
LABEL authors="Levis Nyingi"
FROM eclipse-temurin:17-jdk
RUN mvn clean package -Pprod -DskipTests
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080