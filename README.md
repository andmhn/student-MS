# Student Management System

## How to build

### install java

install jdk 17 from https://www.oracle.com/java/technologies/downloads/#java17

### build jar

navigate to student-api directory
and run 
```
.\mvnw package -DskipTests
```

### start docker container

install docker from https://www.docker.com/

copy the jar from target folder inside student-api directory to docker folder
and start docker container by running following
```
docker compose up
```

the server will start on port 8080
navigate to localhost on browser
```
http://127.0.0.1:8080
```

browse the api in Swagger UI
```
http://127.0.0.1:8080/swagger-ui/index.html
```
