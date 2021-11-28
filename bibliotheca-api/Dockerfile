FROM maven:3.6.3-jdk-11-slim AS target
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn clean package

FROM openjdk:11-jre-slim AS run

RUN groupadd -r spring && useradd --no-log-init -r -g spring spring
USER spring:spring

COPY --from=target /build/target/bibliotheca-api-0.0.1-SNAPSHOT.jar /usr/local/lib/bibliotheca.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/bibliotheca.jar"]