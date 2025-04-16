#FROM ubuntu:latest
FROM openjdk:21-jdk-oraclelinux8
LABEL authors="Joel Pacherie"
ARG src="target/gateway-application-*.jar"
ARG dest="gateway-application.jar"
#COPY ${src} ${dest}
WORKDIR /app
COPY ${src} /app/${dest}
ENTRYPOINT ["java", "-jar", "/app/gateway-application.jar"]
#ENTRYPOINT ["java","-jar","gateway-application.jar"]
