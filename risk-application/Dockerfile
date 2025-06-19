FROM openjdk:21-jdk-oraclelinux8
LABEL authors="Joel Pacherie"
ARG src="target/risk-application-*.jar"
ARG dest="risk-application.jar"
WORKDIR /app
COPY ${src} /app/${dest}
ENTRYPOINT ["java", "-jar", "/app/risk-application.jar"]
