package stock.management.delivery_service.mapping;

import stock.management.delivery_service.entities.Delivery;
import stock.management.kafka.entities.Event;
import stock.management.kafka.entities.Type;

public class DeliveryToEvent {

    public static Event mapDeliveryToEvent(Delivery delivery){
        return new Event(delivery.getEmployee(), delivery.getProduct(), delivery.getQuantity(), Type.DELIVERY);
    };
}
