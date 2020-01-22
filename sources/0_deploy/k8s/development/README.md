Here you can find all needed steps to deploy this project on kubernetes

## ConfigMaps \& Secrets
```sh
sh create-config-maps.sh
```

## Maven

The Maven pom file is configured in order to provide two profiles per each project: development and production. In the development one the exludeDevTools property is set to false so that you can trigger from IntelliJ or any IDE the live reload server to restart the application whenever the code changes.
This capability has to be disabled in production environemnt for security purposes.

## Development environment (using vanilla ingress + apigw)

Run on Minikube.

```sh
    eval $(minikube docker-env)

    cd ../../
    sh build-dev.sh

    kubectl apply -f ./persistentvolumes

    kubectl apply -f ./db
    kubectl apply -f ./storage
    kubectl apply -f ./filemanagementservice
    kubectl apply -f ./apigateway

    kubectl apply -f ./ingress
```

## Development environment (using custom ingress without apiwg)

Run on Minikube.

```sh
    eval $(minikube docker-env)

    cd ../../
    sh build-dev.sh

    kubectl apply -f ./persistentvolumes

    kubectl apply -f ./db
    kubectl apply -f ./storage
    kubectl apply -f ./filemanagementservice

    kubectl apply -f ./customingress
```

## /etc/hosts

```sh
echo "$(minikube ip) osa.localhost" | sudo tee -a /etc/hosts
```

## Test with curl!
```
  # Register new user
  curl --location --request POST 'http://osa.localhost/fms/register' --header 'Content-Type: application/json' --data-raw '{"nickname":"user","email":"user@a.a","password":"user"}'

  # Show uploaded metadatas
  curl --user user@a.a:user --location --request GET 'http://osa.localhost/fms/' --header 'Content-Type: application/json'

  # Upload file metadata
  curl --user user@a.a:user --location --request POST 'http://osa.localhost/fms/' --header 'Content-Type: application/json' --data-raw '{"filename" : "README.md","author" : "localhost gang"}'

  # Upload the file (be careful, it's a POST /{previously-returned-id}
  curl --user user@a.a:user --location --request POST 'http://osa.localhost/fms/2' --form 'file=@./README.md'

  # Get uploaded file url (be careful, it's a GET/{previously-returned-id}
  curl --location --request GET 'http://osa.localhost:9090/fms/' --header 'Content-Type: application/json'

```
