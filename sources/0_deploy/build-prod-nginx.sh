cd ../filemanagementservice
docker build --rm -t fms-prod:v0 . -f Dockerfile-prod

cd ../nginx
docker build --rm -t apigw-nginx:v0 . -f Dockerfile

