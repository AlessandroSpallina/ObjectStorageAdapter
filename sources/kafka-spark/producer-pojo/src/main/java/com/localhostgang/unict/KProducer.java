package com.localhostgang.unict;

import com.localhostgang.unict.common.Utils;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KProducer {
    static org.apache.kafka.clients.producer.KafkaProducer producer;

    public KProducer() {
        final Properties prop = Utils.loadProperties("kafka.properties");
        producer = new org.apache.kafka.clients.producer.KafkaProducer(prop);
    }

    public void produceData (final String toSend) {
        try{
            producer.send(new ProducerRecord("osa-metrics", toSend));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
