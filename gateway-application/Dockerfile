FROM ubuntu:latest
LABEL authors="Joel Pacherie"
ARG src="target/gateway-application-*.jar"
ARG dest="gateway-application.jar"
COPY ${src} ${dest}
ENTRYPOINT ["java","-jar","gateway-application.jar"]