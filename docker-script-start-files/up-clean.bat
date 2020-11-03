echo on
cd ..
call mvn clean -DskipTests
cd docker-script-start-files

docker-compose down --remove-orphans

docker container prune --force
docker container rm $(docker container ls -q)

docker system prune --volumes --force
docker volume rm $(docker volume ls -q)

docker image prune --force
docker image rm pauloportfolio/web-api

docker system df
docker image ls
docker container ls
docker volume ls
rem TIMEOUT 3

rem CLOSE ALL CMD WINDOWS(MEANING OLD CMD DOCKER-COMPOSE WINDOWS)
TASKKILL /F /IM cmd.exe /T

exit