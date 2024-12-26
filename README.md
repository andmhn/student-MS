# Student Management System

## project file structure

student-api/ folder contains java backend code

student-ui/ contains react frontend code

## Run

Firstly, have docker installed from https://www.docker.com/

```
docker compose up
```
the server will start on port 8080
```
http://127.0.0.1:8080
```

# For Development

### Requirements
- **Java version 17 or newer**  https://www.oracle.com/java/technologies/downloads/#java17
- **Node JS** https://nodejs.org/en
- **PostgreSQL**  https://www.postgresql.org/download/

### react frontend

```
cd student-ui
npm install
npm run start --port 3000
```

### java backend

```
cd student-api
.\mvnw spring-boot:run
```

the server will start on port 8080
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
