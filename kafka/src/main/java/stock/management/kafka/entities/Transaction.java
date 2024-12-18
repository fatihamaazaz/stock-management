package stock.management.kafka.entities;

import org.apache.kafka.common.serialization.Serializer;

public class Transaction{
    private String employee;
    private String product;
    private int quantity;
    private Type eventType;
}
