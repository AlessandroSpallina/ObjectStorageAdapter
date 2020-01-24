cd ../filemanagementservice
docker build -t fms-prod:v0 . -f Dockerfile-prod

cd ../nginx
docker build -t apigw-nginx:v0 . -f Dockerfile
