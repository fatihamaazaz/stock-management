package stock.management.sales_service.mapping;

import stock.management.kafka.entities.Event;
import stock.management.kafka.entities.Type;
import stock.management.sales_service.entities.Sale;

public class SaleToEvent {

    public static Event mapSaleToEvent(Sale sale){
        return new Event(sale.getCashierName(), sale.getProduct(), sale.getQuantity(), Type.SALE);
    };
}
