package stock.management.sales_service.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stock.management.kafka.producer.EventProducer;
import stock.management.sales_service.entities.Sale;
import stock.management.sales_service.mapping.SaleToEvent;

@Component
public class SalesService {

    @Autowired
    private EventProducer eventProducer;

    public void addSale(Sale sale){
        eventProducer.sendEvent(SaleToEvent.mapSaleToEvent(sale));
    }

}
