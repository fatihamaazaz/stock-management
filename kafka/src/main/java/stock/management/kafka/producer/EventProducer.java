package stock.management.kafka.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock.management.kafka.entities.Event;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    public void sendEvent(Event event) {
        kafkaTemplate.send(topic, event);
    }
}
