package stock.management.kafka_streams.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.stereotype.Component;
import stock.management.kafka_streams.entities.Event;

public class EventSerdes extends Serdes.WrapperSerde<stock.management.kafka_streams.entities.Event>{

    public EventSerdes() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(stock.management.kafka_streams.entities.Event.class));
    }

    public static Serde<stock.management.kafka_streams.entities.Event> serdes() {
        return new EventSerdes();
    }

}
