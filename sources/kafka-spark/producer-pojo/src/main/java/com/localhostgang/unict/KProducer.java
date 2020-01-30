package com.localhostgang.unict;

import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class KProducer {
    static org.apache.kafka.clients.producer.KafkaProducer producer;

    public KProducer() {
        Properties p = new Properties();
        p.setProperty("bootstrap.servers", System.getProperty("BROKER_HOST", "localhost:9092"));
        p.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        p.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer(p);
    }

    public void produceData (final String toSend) {
        try{
            producer.send(new ProducerRecord(System.getProperty("TOPIC_NAME","osa-metrics"), toSend));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
