docker container prune
docker volume prune
cd ../filemanagementservice
docker build -t fms:v0 . -f Dockerfile-dev
cd ../apigateway
docker build -t apigw:v0 . -f Dockerfile-dev
cd ../deploy
docker-compose up
