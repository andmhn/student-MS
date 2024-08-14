FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application2.jar
ENTRYPOINT ["java", "-jar", "application2.jar"]