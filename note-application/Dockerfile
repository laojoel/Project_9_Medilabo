FROM openjdk:21-jdk-oraclelinux8
LABEL authors="Joel Pacherie"
ARG src="target/note-application-*.jar"
ARG dest="note-application.jar"
COPY ${src} ${dest}
ENTRYPOINT ["java","-jar","note-application.jar"]