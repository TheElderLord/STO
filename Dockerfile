# Dockerfile
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/sto-service.jar sto-service.jar
ENTRYPOINT ["java","-jar","sto-service.jar"]
