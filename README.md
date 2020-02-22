# Progetto Distributed Systems and Big Data
Sviluppato da Alessandro Spallina (O55000439) e Manlio Puglisi (O55000433)

### Specifiche scelte
Progetto b: Object Storage Adapter, DB1 (MySQL), GW1 (nginx), Stats2.

### Prima parte - Homework 1

![alt text](https://raw.githubusercontent.com/AlessandroSpallina/ObjectStorageAdapter/master/readmeimg/osa-homework1.png)

Per la prima parte sono stati sviluppati e dockerizzati l'API Gateway, il File Management Service e il Client.


L'API Gateway, ottenuto configurando nginx, effettua da reverse proxy per i microservizi FileManagementService e MinIO, reindirizzando le richieste dei client al giusto microservizio in funzione del path al quale si effettua la richiesta HTTP. Dimensione massima del body della request e timeout sono stati opportunamente configurati.
Si sarabbe potuto migliorare questo componente permettendogli di acquisire variabili d'ambiente in modo da evitare di rebuildare l'immagine dopo cambiamenti a parametri come "max request body size" o gli hostname dei microservizi.

Il File Management Service è un'applicazione Spring Boot che implementa il pattern MVC, esponendo API in grado di dialogare sia con MinIO sia con un proprio database che tiene conto degli utenti registrati al sistema, occupandosi inoltre della loro autenticazione. Vengono prese in considerazione due tipologie di utenti: "admin" e "user".
Come da specifiche, questo microservizio permette le seguenti azioni:
* Registrazione di un nuovo utente con privilegi minimi
* Upload metadati di un file
* Upload del file i quali metadati sono inviati nell'azione precedente
* Visualizzazione dei file presenti sul sitema
* Download di un file caricato precedentemente
* Eliminazione di un file caricato precedentemente
Per ulteriori informazioni sulle richieste HTTP si rimanda alle specifiche pt1.

Il Client è scritto in Python, si occupa della simulazione di un carico di lavoro paragonabile a quello ottenibile offrendo al pubblico i servizi offerti.
Sono state implementate le funzioni di load così come previste nelle specifiche pt1.
Successivamente alle routine di load vengono esportate delle stat dettagliate di tutte le richieste effettuate:
* tipo di richiesta (GET, POST, DELETE) + eventuale id concatenato alla richiesta
* dimensione del payload in input
* dimensione del payload in output
* tempo di risposta e codice di ritorno.

#### Run dei Microservizi + API Gateway
Istruzioni Docker-compose: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/0_deploy).

Esempi di richieste sono specificati nel README del link sopracitato.

#### Run del client python
Istruzioni per il client python: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/pyclient).

#### Sviluppi futuri
Di seguito alcuni spunti per eventuali sviluppi futuri relativi la pt1:
* Utilizzo best practices docker orientate alla security (gestione user sul container)
* Salvare le password su db non in chiaro (situazione attuale), ma come hash bcrypt
* Evitare di ritornare all'utente id che corrispondono alle primary key del database

#### Per la parte 2, vedere branch apposito
