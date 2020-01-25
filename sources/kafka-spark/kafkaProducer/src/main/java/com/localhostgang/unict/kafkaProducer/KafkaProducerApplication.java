package com.localhostgang.unict.kafkaProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) throws IOException {
		//SpringApplication.run(KafkaProducerApplication.class, args);
		CSVStream csvStream = new CSVStream();
	}

}
