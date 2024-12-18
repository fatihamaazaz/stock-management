package stock.management.kafka.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock.management.kafka.entities.Transaction;

@Component
public class TransactionProducer {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendMessage(Transaction transaction) {
        kafkaTemplate.send("transaction", transaction);
    }
}
