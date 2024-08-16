# Student Management System

## project file structure

student-api/ folder contains java backend code

student-ui/ contains react frontend code

docker/ contains docker files

## How to build
### step 1: build react frontend
get nodejs installed from https://nodejs.org/en

For first time
```
cd student-ui
```
```
npm install
npm run build
```
you will find a dist folder is created

to build it next time
```
npm run build
```

### step 2: build java backend

install jdk 17 from https://www.oracle.com/java/technologies/downloads/#java17

### build jar

navigate to student-api directory
and run 
```
cd student-api
```
```
.\mvnw package -DskipTests
```

### step 3: start docker container

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
