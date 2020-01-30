## Avviare l'applicazione

Una volta scaricato il progetto, recati nella directory del progetto e apri il terminale.

```
cd sources/0_deploy
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

A seconda di quale delle due build tu abbia eseguito, esegui rispettivamente
```
docker-compose -f ./docker-compose-prod-nginx.yml up

```
oppure
```
docker-compose -f ./docker-compose-dev-nginx.yml up
```

## Esempi di richieste

```
  # Registra un nuovo utente
  curl --location --request POST 'http://osa.localhost/fms/register' --header 'Content-Type: application/json' --data-raw '{"nickname":"user","email":"user@a.a","password":"user"}'

  # Mostra i metadati caricati
  curl --user user@a.a:user --location --request GET 'http://osa.localhost/fms/' --header 'Content-Type: application/json'

  # Upload i metadata di un file
  curl --user user@a.a:user --location --request POST 'http://osa.localhost/fms/' --header 'Content-Type: application/json' --data-raw '{"filename" : "README.md","author" : "localhost gang"}'

  # Upload il file file (attenzione, deve essere una POST /{id-precedentemente-returnato}
  curl --user user@a.a:user --location --request POST 'http://osa.localhost/fms/2' --form 'file=@./README.md'

  # Get url del file caricato (attenzione, è una GET/{id-precedentemente-returnato}
  curl --user user@a.a:user --location --request GET 'http://osa.localhost/fms/2' --header 'Content-Type: application/json'

```
