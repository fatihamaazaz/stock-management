package stock.management.kafka_streams.configuration;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import stock.management.kafka_streams.entities.Event;
import stock.management.kafka_streams.processors.HistoryProcessing;
import stock.management.kafka_streams.processors.ProductsProcessing;
import stock.management.kafka_streams.serdes.EventSerdes;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaStreamsPipelineConfig {

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    @Autowired
    private HistoryProcessing historyProcessing;

    @Autowired
    private ProductsProcessing productsProcessing;

    @Bean
    public KStream<String, Event> kStream(StreamsBuilder kStreamBuilder) {

        KStream<String, Event> stream = kStreamBuilder.stream(topic, Consumed.with(Serdes.String(), EventSerdes.serdes()));

        historyProcessing.process(stream);
        productsProcessing.process(stream);

        return stream;
    }


}
