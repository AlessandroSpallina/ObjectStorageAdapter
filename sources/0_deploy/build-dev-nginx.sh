cd ../filemanagementservice
docker build --rm -t fms:v0 . -f Dockerfile-dev

cd ../nginx
docker build --rm -t apigw-nginx:v0 . -f Dockerfile
