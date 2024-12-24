package stock.management.kafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    private String employee;
    private String product;
    private int quantity;
    private Type eventType;
}
