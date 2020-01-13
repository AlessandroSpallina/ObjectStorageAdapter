docker container prune
docker volume prune
docker build -t fms:v0 . -f ../filemanagementservice/Dockerfile-dev
docker build -t apigw:v0 . -f ../apigateway/Dockerfile-dev
docker-compose up
