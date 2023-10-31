FROM sbtscala/scala-sbt:eclipse-temurin-focal-11.0.17_8_1.8.2_2.13.10 AS build

ARG SERVICE_NAME

COPY . /root/
WORKDIR /root
ENV SBT_OPT="-Xms2G -Xmx2G -Xss3M"
RUN sbt "project ${SERVICE_NAME}" assembly

FROM eclipse-temurin:11-jre-focal

ARG SERVICE_NAME
ARG SERVICE_PORT

RUN mkdir -p /opt/app/
COPY --from=build /root/${SERVICE_NAME}/target/scala-2.13/*.jar opt/app/app.jar
WORKDIR /opt/app
EXPOSE ${SERVICE_PORT}
CMD ["java", "-jar", "app.jar"]
