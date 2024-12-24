package stock.management.kafka_streams.serdes;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import stock.management.kafka_streams.mappers.JsonMapper;

import java.nio.charset.StandardCharsets;

public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> destinationClass;

    public JsonDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        try {
            System.out.println("tryyyyyyyyiiiiiing "+ new String(bytes, StandardCharsets.UTF_8));
            return JsonMapper.readFromJson(new String(bytes, StandardCharsets.UTF_8), destinationClass);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message", e);
        }
    }
}
