# docker volume prune
docker container prune
cd ../ObjectStorageAdapter/sources/filemanagementservice
docker build -t fms:v0 . -f Dockerfile-dev
cd ../ObjectStorageAdapter/sources/apigateway
docker build -t apigw:v0 . -f Dockerfile-dev
cd ..
cd deploy
docker-compose up
