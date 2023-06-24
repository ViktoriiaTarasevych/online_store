#
# Build stage
#
FROM eclipse-temurin:17-jdk AS build
COPY src /app/src
COPY pom.xml /app

ENV MAVEN_HOME /usr/share/maven

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

#
# Package stage
#


FROM eclipse-temurin:17-jdk
EXPOSE 8080
VOLUME /tmp
COPY /app/online_store-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
