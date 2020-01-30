### Per avviare produttore e consumatore Kafka

Per prima cosa Bisogna mandare in esecuzione il broker:
```
# se l'immagine non è presente:
docker pull landoop/fast-data-dev 

docker run --rm --net=host landoop/fast-data-dev 
```
Successivamente aprire il producer Kafka, ossia lo spout da IntelliJ
Recarsi su producer-pojo/src/main/java/com/localhostgang/unict/CSVStream.java
e modificare la stringa contenente "/home/manlio/Scrivania/metrics.csv" con la directory dove situato il file metrics.csv


Infine aprire l'applicazione Scala (sparkConsumer) e buildarla via maven.
Eseguire lo script
```
 spark-submit \
--master local \
--class org.example.App \
/path/assoluto/dello-1.0-SNAPSHOT-jar-with-dependencies.jar
```
