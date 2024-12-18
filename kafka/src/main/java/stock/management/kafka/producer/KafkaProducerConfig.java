package stock.management.kafka.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import stock.management.kafka.entities.Transaction;
import stock.management.kafka.serializing.TransactionSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfig {

    @Bean
    public <V> ProducerFactory<String, V> producerFactory(Class<? extends Serializer<V>> valueSerializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public <V> KafkaTemplate<String, V> kafkaTemplate(Class<? extends Serializer<V>> valueSerializer) {
        return new KafkaTemplate<>(producerFactory(valueSerializer));
    }

    @Bean
    public KafkaTemplate<String, Transaction> transactionKafkaTemplate() {
        return this.kafkaTemplate(TransactionSerializer.class);
    }

}
