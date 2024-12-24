package stock.management.delivery_service.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stock.management.delivery_service.entities.Delivery;
import stock.management.delivery_service.mapping.DeliveryToEvent;
import stock.management.kafka.producer.EventProducer;

@Component
public class DeliveryService {

    @Autowired
    private EventProducer eventProducer;

    public void addDelivery(Delivery delivery){
        eventProducer.sendEvent(DeliveryToEvent.mapDeliveryToEvent(delivery));
    }

}
