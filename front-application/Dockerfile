FROM openjdk:21-jdk-oraclelinux8
LABEL authors="Joel Pacherie"
ARG src="target/front-application-*.jar"
ARG dest="front-application.jar"
WORKDIR /app
COPY ${src} /app/${dest}
ENTRYPOINT ["java", "-jar", "/app/front-application.jar"]
