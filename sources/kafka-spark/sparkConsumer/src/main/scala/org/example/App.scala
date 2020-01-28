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
    val ssc = new StreamingContext(conf, Seconds(30))

    val kafkaParams = Map[String, Object] (
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "consumer-group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // val topic = Array("metric")
    val topic = Array("osa-metrics")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topic, kafkaParams)
    )


    val lines = stream.map(_.value)
    // val countGET = lines.flatMap(line => line.split(" ")).filter(word => word.contains("GET")).map(word => (word, 1))
    // val reduced = countGET.reduceByKey(_ + _)
    //reduced.saveAsTextFiles("provasalvataggio/prova")
    //lines.saveAsTextFiles("provasalvataggio/prova")

    stream.saveAsTextFiles("provasalvataggio/prova")

    // Sta println l'ho provata per vedere se printa su schermo le lines che tecnicamente si pulla dal broker
    println("#################   "+lines+"   #################")
    // spoiler: non lo fa.

    ssc.start()
    ssc.awaitTermination()
  }
}
