# Build stage
#
FROM maven:3.6.3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -X


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar slotservice-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "slotservice-1.0.jar"]