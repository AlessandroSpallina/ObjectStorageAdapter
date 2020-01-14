docker container prune
# docker volume prune

docker rmi fms:v0
docker rmi apigw:v0

cd ../filemanagementservice
#mvn package
docker build -t fms:v0 . -f Dockerfile-dev

cd ../apigateway
#mvn package
docker build -t apigw:v0 . -f Dockerfile-dev

cd ../deploy
docker-compose up
