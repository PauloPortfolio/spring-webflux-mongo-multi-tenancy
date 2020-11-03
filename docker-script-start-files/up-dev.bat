echo on
REM SETTING JDK11 AS DEFAULT
set JAVA_HOME=C:\Program Files\java\AdoptOpenJDK-11.0.8+10

REM MAVEN-CLEAN-PACKAGE WITHOUT TESTS
cd ..
call mvn clean package -DskipTests
cd docker-script-start-files

docker-compose down --remove-orphans

docker container prune --force
docker container rm $(docker container ls -q)

docker system prune --volumes --force
docker volume rm $(docker volume ls -q)

docker image prune --force

docker system df
docker image ls
docker container ls
docker volume ls
rem TIMEOUT 3

rem START THE COMPOSE CONTAINERS
docker-compose up --build --force-recreate

pause

