Step 1:

```
docker run --rm --net=host landoop/fast-data-dev 
```


Step 2:

Start Kafka Producer (from IDE), that's the Spout


Step3:
```
./spark-submit --master local --class com.dslab.App /home/manlio/Scrivania/spark-kafka-consumer-PROVA/target/spark-streaming-twitter_hashtag-1.0-SNAPSHOT-jar-with-dependencies.jar
```
