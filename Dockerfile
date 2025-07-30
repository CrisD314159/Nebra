FROM amazoncorretto:17-alpine-jdk
LABEL authors="cristiandavidvargasloaiza"
COPY target/Nebra-0.0.1-SNAPSHOT.jar Nebra-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/Nebra-1.0.0.jar"]