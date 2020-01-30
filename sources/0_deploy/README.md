Una volta scaricato il progetto, recati nella directory del progetto e apri il terminale.

```
cd ObjectStorageAdapter/sources/0_deploy
```
Per buildare le immagini docker in produzione esegui
```
./build-prod-nginx.sh
```
Altrimenti, per buildare le immagini docker in fase di sviluppo esegui
```
./build-dev-nginx.sh
```

Successivamente, spostati da terminale nella cartella ```docker-compose```
```
cd docker-compose
```
E, a seconda di quale delle due build tu abbia eseguito, esegui rispettivamente
```
docker-compose -f docker-compose-prod-nginx up

```
oppure
```
docker-compose -f docker-compose-dev-nginx up
```
