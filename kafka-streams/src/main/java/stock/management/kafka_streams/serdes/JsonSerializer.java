package stock.management.kafka_streams.serdes;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import stock.management.kafka_streams.mappers.JsonMapper;

import java.nio.charset.StandardCharsets;

public class JsonSerializer<T> implements Serializer<T> {

    public JsonSerializer() {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null)
            return null;

        try {
            return JsonMapper.writeToJson(data).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

}
