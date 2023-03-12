#
# Build stage
#
FROM maven:3.8-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
# Add JavaFX
RUN curl -fsSL -o /tmp/javafx-sdk-19.zip https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_linux-x64_bin-sdk.zip \
  && cd /tmp/ \
  && unzip /tmp/javafx-sdk-19.zip \
#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/target/tfg-1.0.jar /usr/local/lib/tfg.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/tfg.jar"]