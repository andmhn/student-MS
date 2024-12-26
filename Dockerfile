# React Build
#===================

FROM node:20-alpine AS development

WORKDIR /student-ui
COPY student-ui/package.json /student-ui/package.json
COPY student-ui/package-lock.json /student-ui/package-lock.json

RUN npm ci
COPY student-ui /student-ui

FROM development AS ui-build
RUN ["npm", "run", "build"]

# Spring Boot Build
#===================

FROM --platform=$BUILDPLATFORM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /student-api
COPY student-api/pom.xml /student-api/pom.xml
RUN mvn dependency:go-offline

COPY student-api/src /student-api/src
COPY --from=ui-build /student-ui/build /student-api/target/classes/static

RUN mvn package -DskipTests

# Spring Boot Run
#=================

FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/student-api/target/**.jar
COPY --from=builder ${DEPENDENCY} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
