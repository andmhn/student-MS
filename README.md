# Student Management System

## project file structure

student-api/ folder contains java backend code

student-ui/ contains react frontend code

docker/ contains docker files

## Requirements
- **Java version 17 or newer**

    install jdk 17 from https://www.oracle.com/java/technologies/downloads/#java17

- **Node JS**

    get nodejs installed from https://nodejs.org/en

- **Docker**

    
    install docker from https://www.docker.com/


## Build whole project with with script

Simple double click **build_and_run.cmd** batch script in root folder It will build react project , spring boot application and create and run docker containers. 

or run it from terminal
```
./build_and_run.cmd
```

the server will start on port 8080
```
http://127.0.0.1:8080
```

## Build manually

### step 1: build react frontend

```
cd student-ui

npm install
npm run build
```
you will find a dist folder is created


### step 2: build java backend

### build jar

navigate to student-api directory
and run 
```
cd student-api

.\mvnw package -DskipTests
```

### step 3: start docker container

copy the jar from target folder inside student-api directory to docker folder
and start docker container by running following
```
cd docker

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

### test the api with pytest

```
cd tests
pytest -v
```

## develop frontend seperately

```
cd student-ui

npm run start --port 3000
```

since only http://localhost:3000 is allowed in cors 