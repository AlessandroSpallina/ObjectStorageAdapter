package org.example

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**
 * Hello world!
 *
 */
object App {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("spark-consumer-kafka")
    val ssc = new StreamingContext(conf, Seconds(1))

    val kafkaParams = Map[String, Object] (
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "consumer-group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topic = Array("metric")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topic, kafkaParams)
    )

    val lines = stream.map(_.value)
    val countGET = lines.flatMap(line => line.split(" ")).filter(word => word.contains("get")).map(word => (word, 1))
    val reduced = countGET.reduceByKey(_ + _)
    reduced.saveAsTextFiles("provasalvataggio") //dopo hdfs:// ci va il nome completo dell'host name dov'è il master

    ssc.start()
    ssc.awaitTermination()
  }
}
