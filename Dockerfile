#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests


#
# Package stage
#
FROM openjdk:17-jdk-slim

EXPOSE 8080
COPY /online_store-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=root



