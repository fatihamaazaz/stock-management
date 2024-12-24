package stock.management.kafka_streams.exceptions;

public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }
}
