where /q npm    || ECHO Cound not find npm in path.    && EXIT /B
where /q java   || ECHO Cound not find java in path.   && EXIT /B
where /q docker || ECHO Cound not find docker in path. && EXIT /B

cd student-ui

CALL npm install

CALL npm run build

cd ..

cd student-api

CALL .\mvnw.cmd package -DskipTests

del ..\docker\student-api-0.0.1.jar

copy target\student-api-0.0.1.jar ..\docker

cd ..\docker

docker cp .\student-api-0.0.1.jar studentMS:/application.jar

docker compose up
