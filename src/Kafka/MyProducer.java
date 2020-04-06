package Kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<Object, Object> producer = new KafkaProducer<>(prop);

        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
        }

        producer.close();
    }
}
