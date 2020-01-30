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

    val ssc = new StreamingContext(conf, Seconds(sys.env.getOrElse("BATCH_TIME", "60").toLong))

    val kafkaParams = Map[String, Object] (
      "bootstrap.servers" -> sys.env.getOrElse("BROKER_HOST", "localhost:9092"),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> sys.env.getOrElse("CONSUMER_GROUP_ID","consumer-group"),
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // val topic = Array("metric")
    val topic = Array(sys.env.getOrElse("TOPIC_NAME","osa-metrics"))
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topic, kafkaParams)
    )


    val lines = stream.map(_.value)

    val letture = lines.filter(line => {
      line.split(" ")(1) == "GET"
    }).map(w => {
      ("read", w.split(" ")(5).toFloat)
    })//.reduceByKey(_ + _)
    letture.saveAsTextFiles("metrics/read/record-")

    val scritture = lines.filter(line => {
      line.split(" ")(1) == ("POST") || line.split(" ")(1) == ("DELETE")
    }).map(w => {
      ("write", w.split(" ")(5).toFloat)
    })
    scritture.saveAsTextFiles("metrics/write/record-")

    ssc.start()
    ssc.awaitTermination()
  }
}
