FROM eclipse-temurin:17-jre

RUN mkdir -p /opt/application
COPY *-runner.jar /opt/application/leonergy.jar
WORKDIR /opt/application
CMD [ "java", "-jar", "leoenergy.jar" ]