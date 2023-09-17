FROM openjdk:17-jdk-alpine
ARG JAR-FILE=build/*.jar
COPY ./build/libs/multi-tenancy-hostel-software-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8800
LABEL authors="MosTech"

ENTRYPOINT ["java", "-jar","/app.jar"]