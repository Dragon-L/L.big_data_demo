package Kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Properties;

public class MyStreams {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> lines = builder.stream("test1");

//        KStream<String, String> upperlines = lines.mapValues(value -> value.toUpperCase());
        lines.groupByKey()
                .windowedBy(TimeWindows.of(500))
                .count(Materialized.with(Serdes.String(), Serdes.Long()))
                .suppress(Suppressed.untilTimeLimit(Duration.ofMillis(100), Suppressed.BufferConfig.maxBytes(999999)))
                .toStream()
                .map((key, value) -> new KeyValue<>(key.key(), value))
                .to("window", Produced.with(Serdes.String(), Serdes.Long()));

//        upperlines.to("upper");
//
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
