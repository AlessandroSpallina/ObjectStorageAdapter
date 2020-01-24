#docker container prune
# docker volume prune

#docker rmi fms:v0
#docker rmi apigw:v0
#docker rmi minio-custom:v0

cd ../filemanagementservice
docker build -t fms:v0 . -f Dockerfile-dev

cd ../nginx
docker build -t apigw-nginx:v0 . -f Dockerfile

#cd ../minio
#docker build -t minio-custom:v0 .

#cd ../deploy
#docker-compose up
