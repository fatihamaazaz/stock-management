package stock.management.kafka.serializing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import stock.management.kafka.entities.Transaction;

public class TransactionSerializer implements Serializer<Transaction> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Transaction transaction) {
        try {
            return objectMapper.writeValueAsBytes(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing transaction", e);
        }
    }
}
