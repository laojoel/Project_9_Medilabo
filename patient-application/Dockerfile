FROM ubuntu:latest
LABEL authors="Joel Pacherie"
ARG src="target/patient-application-*.jar"
ARG dest="patient-application.jar"
COPY ${src} ${dest}
ENTRYPOINT ["java","-jar","patient-application.jar"]