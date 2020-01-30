# Progetto Distributed Systems and Big Data
Sviluppato da Alessandro Spallina (O55000439) e Manlio Puglisi (O55000433)

### Specifiche scelte
Progetto b: Object Storage Adapter, DB1 (MySQL), GW1 (nginx), Stats2.

### Prima parte - Homework 1

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/pt2/readmeimg/osa-homework1.png?token=AHHOYZ5SZLHRK33A34NI3RK6HRCR4)

Per la prima parte sono stati sviluppati e dockerizzati l'API Gateway, il File Management Service e il Client.


L'API Gateway è stato scritto configurando nginx, effettua il reverse proxy reindirizzando le richieste dei client che provano ad accedere ai servizi dello Storage e maschera gli host a cui le richieste vengono inviate. Si occupa inoltre del reindirizzamento delle richieste di download verso MinIO;


Il File Management Service è un'applicazione Spring Boot che implementa il pattern MVC, esponendo API in grado di dialogare sia con MinIO sia con un proprio database che tiene conto degli utenti registrati al sistema, occupandosi inoltre della loro autenticazione. Le tipologie di utenti che operano all'interno del sistema sono due: "admin" e "user".
Come richiesto da specifiche, seguendo le richieste specificate seguendo il link nel paragrafo successivo, l'utente "user" può ottenere la lista contenente i metadati dei suoi file caricati su MinIO - specificando un id dopo / si ottiene, se presente, la lista di specifiche del file associato al dato id - mentre l'utente "admin" ottiene la lista di tutti i metadati presenti nel database associato al File Management Service.


Il Client è scritto in Python, si occupa della simulazione delle richieste effettuate da potenziali utenti, salvando su file .csv: tipo di richiesta (GET, POST, DELETE), eventuale id concatenato alla richiesta, dimensione del payload in input, dimensione del payload in output, tempo di risposta e codice di ritorno.
I vari valori richiesti dalla consegna (n1, n2, n3, n4, p1, p2) sono specificati in sources/pyclient/docker-compose.yml > environment.
Il file .csv viene salvato su file system, anche il nome del file e il path dove viene salvato sono parametrizzati.

#### Avviare l'applicazione
Istruzioni Docker-compose: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/0_deploy/docker-compose).

Esempi di richieste sono specificati nel README del link sopracitato.
