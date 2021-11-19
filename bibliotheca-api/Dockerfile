FROM maven:3.6.3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim AS run

RUN groupadd -r spring && useradd --no-log-init -r -g spring spring
USER spring:spring

COPY --from=build /home/app/target/bibliotheca-api-0.0.1-SNAPSHOT.jar /usr/local/lib/bibliotheca.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/bibliotheca.jar"]