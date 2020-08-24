package Kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MyProducer {
    public static void main(String[] args) throws InterruptedException {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");

        KafkaProducer<Object, Object> producer = new KafkaProducer<>(prop);

        for (int i = 0; i < 20; i++) {
//            producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
//            producer.send(new ProducerRecord<>("test1", "key", (long)i));
            producer.send(new ProducerRecord<>("test1", (long) i));
            TimeUnit.MILLISECONDS.sleep(100);
        }

        producer.close();
    }
}
