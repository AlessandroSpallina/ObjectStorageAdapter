cd ../filemanagementservice
docker build -t fms:v0 . -f Dockerfile-dev

cd ../nginx
docker build -t apigw-nginx:v0 . -f Dockerfile
