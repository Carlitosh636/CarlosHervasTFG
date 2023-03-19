#
# Build stage
#

FROM maven:3.8.3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
# Package stage
#
FROM ubuntu:20.04
RUN sudo apt-get update && sudo apt-get install libX11.so.6
FROM openjdk:17-alpine
COPY --from=build /home/app/target/tfg-1.0.jar /usr/local/lib/tfg.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/tfg.jar"]