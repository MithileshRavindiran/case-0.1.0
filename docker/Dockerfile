FROM openjdk:11-jdk-slim as builder

ARG JAR_FILE=build/libs/klmAssignment-0.0.1.jar
EXPOSE 8080
ADD ${JAR_FILE} klmAssignment.jar

ENTRYPOINT ["java","-jar","/klmAssignment.jar"]