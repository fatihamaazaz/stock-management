package stock.management.kafka.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock.management.kafka.entities.Event;

@Component
public class TransactionConsumer {

    @KafkaListener(topics = "transactions", groupId = "user-group", containerFactory = "transactionKafkaListenerContainerFactory")
    public void listenGroupFoo(Event transaction) {
        System.out.println("Received Message: " + transaction.toString());
    }

}
