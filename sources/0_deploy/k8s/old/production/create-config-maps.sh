kubectl create configmap osa-db-env-file --from-env-file=./db/db.properties --save-config
kubectl get configmap osa-db-env-file -o yaml > ./db/osa-db-configmap.yml

kubectl create secret generic osa-db-secret-file --from-env-file=./db/db.secrets --save-config
kubectl get secret osa-db-secret-file -o yaml > ./db/osa-db-secret-file.yml

# ---

kubectl create configmap osa-filemanagementservice-env-file --from-env-file=./filemanagementservice/filemanagementservice.properties --save-config
kubectl get configmap osa-filemanagementservice-env-file -o yaml > ./filemanagementservice/osa-filemanagementservice-configmap.yml

kubectl create secret generic osa-filemanagementservice-secret-file --from-env-file=./filemanagementservice/filemanagementservice.secrets --save-config
kubectl get secret osa-filemanagementservice-secret-file -o yaml > ./filemanagementservice/osa-filemanagementservice-secret-file.yml

# ---

kubectl create configmap osa-storage-env-file --from-env-file=./storage/storage.properties --save-config
kubectl get configmap osa-storage-env-file -o yaml > ./storage/osa-storage-configmap.yml

kubectl create secret generic osa-storage-secret-file --from-env-file=./storage/storage.secrets --save-config
kubectl get secret osa-storage-secret-file -o yaml > ./storage/osa-storage-secret-file.yml
