package stock.management.kafka_streams.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String employee;
    private String product;
    private Integer quantity;
    private String eventType;
}

