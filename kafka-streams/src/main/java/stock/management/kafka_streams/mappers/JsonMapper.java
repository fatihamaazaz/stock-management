package stock.management.kafka_streams.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import stock.management.kafka_streams.exceptions.MappingException;

public class JsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readFromJson(String json, Class<T> clazz) throws MappingException {
        try {
            System.out.println("Received JSON: " + json);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new MappingException("Mapping from json failed");
        }
    }


    public static String writeToJson(Object obj) throws MappingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new MappingException("Mapping to json failed");
        }
    }


}
