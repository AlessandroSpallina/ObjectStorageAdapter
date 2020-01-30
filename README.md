# Progetto Distributed Systems and Big Data
Sviluppato da Alessandro Spallina (O55000439) e Manlio Puglisi (O55000433)

### Specifiche scelte
Progetto b: Object Storage Adapter, DB1 (MySQL), GW1 (nginx), Stats2.

### Seconda parte - Homework 2

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/master/readmeimg/osa-homework2.png?token=AHHOYZ3Z5XZFK5DFURYZUCC6HSIGA)

Come richiesto da specifiche, è stato eseguito il porting su Kubernetes di quanto già realizzato. Di fatto, nelle directories [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/0_deploy/k8s/production-nginx) e [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/0_deploy/k8s/development-nginx) è possibile trovare la versione portata su Kubernetes dei rami, rispettivamente, -prod e -dev del primo homework.

La Saga del File Management Service è stata realizzata seguendo la macchina a stati rappresentata in figura sottostante:

![alt text](https://raw.githubusercontent.com/PManlio/ObjectStorageAdapter/master/readmeimg/osa-homework2-saga.png?token=AHHOYZ6H7ICHD7UFYOTNSY26HSI6M)

La parte riguardante Kafka e Spark ha alcune lacune.
Sono stati riscontrati numerosi problemi nel deployment su Kubernetes a causa di una nostra negligenza per cui non abbiamo direttamente dockerizzato lo spout e il consumer spark, arrivando al punto di non avere idea dei motivi per cui si siano generati problemi di manifesto, che non riusciamo a risolvere nei tempi stabiliti dalla consegna.
Inoltre, non è stato effettuato il calcolo delle metriche come richiesto.
Tuttavia, lo spout e il consumer sono entrambi funzionanti, ed effettivamente lo stream di informazioni viaggia correttamente.
I messaggi vengono ricevuti dal consumer e filtrati per "lettura" (nel caso in cui il messaggio letto contenga una GET) o per "scrittura" (nel caso in cui il messaggio letto contenga una POST o una DELETE).
Se è dunque vero che non è stato fatto un calcolo esaustivo delle metriche, i file di testo che vengono salvati portano comunque un elenco di messaggi, accumulati in un batch time, che esplicitano il tempo di risposta del server alla chiamata simulata, rendendo quindi possibile una futura analisi dei dati ottenuti.
Al seguente link le informazioni per avviare la comunicazione tra spout, broker e consumer: [link](https://github.com/PManlio/ObjectStorageAdapter/tree/pt2/sources/kafka-spark)
