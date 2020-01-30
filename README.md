# Progetto Distributed Systems and Big Data
Sviluppato da Alessandro Spallina (O55000439) e Manlio Puglisi (O55000433)

### Specifiche scelte
Progetto b: Object Storage Adapter, DB1 (MySQL), GW1 (nginx), Stats2.

### Prima parte - Homework 1

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/master/readmeimg/osa-homework1.png?token=AHHOYZ62UD5WDQPWP2X5NQ26HSH4E)

Per la prima parte sono stati sviluppati e dockerizzati l'API Gateway, il File Management Service e il Client.


L'API Gateway è stato scritto configurando nginx, effettua il reverse proxy reindirizzando le richieste dei client che provano ad accedere ai servizi dello Storage e maschera gli host a cui le richieste vengono inviate. Si occupa inoltre del reindirizzamento delle richieste di download verso MinIO;


Il File Management Service è un'applicazione Spring Boot che implementa il pattern MVC, esponendo API in grado di dialogare sia con MinIO sia con un proprio database che tiene conto degli utenti registrati al sistema, occupandosi inoltre della loro autenticazione. Le tipologie di utenti che operano all'interno del sistema sono due: "admin" e "user".
Come richiesto da specifiche, seguendo le richieste specificate seguendo il link nel paragrafo successivo, l'utente "user" può ottenere la lista contenente i metadati dei suoi file caricati su MinIO - specificando un id dopo / si ottiene, se presente, la lista di specifiche del file associato al dato id - mentre l'utente "admin" ottiene la lista di tutti i metadati presenti nel database associato al File Management Service.


Il Client è scritto in Python, si occupa della simulazione delle richieste effettuate da potenziali utenti, salvando su file .csv: tipo di richiesta (GET, POST, DELETE), eventuale id concatenato alla richiesta, dimensione del payload in input, dimensione del payload in output, tempo di risposta e codice di ritorno.
I vari valori richiesti dalla consegna (n1, n2, n3, n4, p1, p2) sono specificati in sources/pyclient/docker-compose.yml > environment.
Il file .csv viene salvato su file system, anche il nome del file e il path dove viene salvato sono parametrizzati.

#### Avviare l'applicazione
Istruzioni Docker-compose: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/0_deploy).

Esempi di richieste sono specificati nel README del link sopracitato.

#### Avviare le metriche
Istruzioni per il client python: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/pyclient).


### Seconda parte - Homework 2

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/master/readmeimg/osa-homework2.png?token=AHHOYZ3Z5XZFK5DFURYZUCC6HSIGA)

Come richiesto da specifiche, è stato eseguito il porting su Kubernetes di quanto già realizzato. Di fatto, nelle directories [link](https://github.com/PManlio/ObjectStorageAdapter/tree/master/sources/0_deploy/k8s/production-nginx) e [link](https://github.com/PManlio/ObjectStorageAdapter/tree/master/sources/0_deploy/k8s/development-nginx) è possibile trovare la versione portata su Kubernetes dei rami, rispettivamente, -prod e -dev del primo homework.

La Saga del File Management Service è stata realizzata seguendo la macchina a stati rappresentata in figura sottostante:

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/master/readmeimg/osa-homework2-saga.png?token=AHHOYZ6H7ICHD7UFYOTNSY26HSI6M)

La parte riguardante Kafka e Spark ha alcune lacune.
Sono stati riscontrati numerosi problemi nel deployment su Kubernetes a causa di una nostra negligenza per cui non abbiamo direttamente dockerizzato lo spout e il consumer spark, arrivando al punto di non avere idea dei motivi per cui si siano generati problemi di manifesto, che non riusciamo a risolvere nei tempi stabiliti dalla consegna.
Inoltre, non è stato effettuato il calcolo delle metriche come richiesto.
Tuttavia, lo spout e il consumer sono entrambi funzionanti, ed effettivamente lo stream di informazioni viaggia correttamente.
I messaggi vengono ricevuti dal consumer e filtrati per "lettura" (nel caso in cui il messaggio letto contenga una GET) o per "scrittura" (nel caso in cui il messaggio letto contenga una POST o una DELETE).
Se è dunque vero che non è stato fatto un calcolo esaustivo delle metriche, i file di testo che vengono salvati portano comunque un elenco di messaggi, accumulati in un batch time, che esplicitano il tempo di risposta del server alla chiamata simulata, rendendo quindi possibile una futura analisi dei dati ottenuti.
Al seguente link le informazioni per avviare la comunicazione tra spout, broker e consumer: [link]()
