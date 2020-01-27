Step 1:

```
docker run --rm --net=host landoop/fast-data-dev 
```


Step 2:

Start Kafka Producer (from IDE), that's the Spout


Step3:
```
 spark-submit \
--master local \
--class org.example.App \
/home/manlio/Scrivania/'progetto sistemi distribuiti'/ObjectStorageAdapter/sources/kafka-spark/sparkConsumer/target/provaScala-1.0-SNAPSHOT-jar-with-dependencies.jar
```
