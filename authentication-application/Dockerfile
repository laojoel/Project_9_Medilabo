FROM openjdk:21-jdk-oraclelinux8
LABEL authors="Joel Pacherie"
ARG src="target/authentication-application-*.jar"
ARG dest="authentication-application.jar"
WORKDIR /app
COPY ${src} /app/${dest}
ENTRYPOINT ["java", "-jar", "/app/authentication-application.jar"]
