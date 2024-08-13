cd student-api

CALL .\mvnw.cmd package -DskipTests

del ..\docker\student-api-0.0.1.jar

copy target\student-api-0.0.1.jar ..\docker

cd ..\docker

docker compose up
