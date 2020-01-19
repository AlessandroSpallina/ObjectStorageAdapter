cd ../filemanagementservice
docker build -t fms-prod:v0 . -f Dockerfile-prod

cd ../apigateway
docker build -t apigw-prod:v0 . -f Dockerfile-prod

cd ../minio
docker build -t minio-custom:v0 .
