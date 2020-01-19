Here you can find all needed steps to deploy this project on kubernetes

## ConfigMaps \& Secrets
```
sh create-config-maps.sh
```

## Maven

The Maven pom file is configured in order to provide two profiles per each project: development and production. In the development one the exludeDevTools property is set to false so that you can trigger from IntelliJ or any IDE the live reload server to restart the application whenever the code changes.
This capability has to be disabled in production environemnt for security purposes.

## Production environment

Run on Minikube.

```
    eval $(minikube docker-env)
    sh ../../build-prod.sh
    
    kubectl apply -f ./db
    kubectl apply -f ./storage
    kubectl apply -f ./persistentvolumes
    kubectl apply -f ./filemanagementservice
    kubectl apply -f ./apigateway
```

## /etc/hosts

```sh
echo "$(minikube ip) osa.localhost" | sudo tee -a /etc/hosts
```
