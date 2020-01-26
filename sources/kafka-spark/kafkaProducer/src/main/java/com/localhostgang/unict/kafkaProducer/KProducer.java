package com.localhostgang.unict.kafkaProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KProducer {
    // static KafkaProducer producer;
    final Producer<Long, String> producer;

    public KProducer() {
        producer = createProducer();
    }

    private static Producer<Long, String> createProducer() {
        Properties properties = new Properties();
        // properties = Utils.loadProperties("kafka.properties");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }


    // suggerimento: cambia da "public" a "static"
    public void runProducer(String metric) throws Exception {
        try {
            // metodo che richiamo in CSVUtils
            producer.send(new ProducerRecord("metric", metric.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }/*finally {
           // producer.flush();
           // producer.close();
        } */
    }
}
