package stock.management.kafka_streams.processors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import stock.management.kafka_streams.entities.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Component
public class ProductsProcessing {

    @Value(value = "${spring.datasource.username}")
    private String dbUser;

    @Value(value = "${spring.datasource.password}")
    private String dbPassword;

    @Value(value = "${spring.datasource.url}mydb")
    private String dbUrl;

    public void process(KStream<String, Event> stream){
        KGroupedStream<String, Integer> quantityByProductCodeBar = stream
                .map((key, value) -> new KeyValue<>(value.getProduct(),
                            value.getEventType().equals("SALE") ? (-value.getQuantity()) : Integer.valueOf(value.getQuantity())))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()));

        KTable<String, Integer> totalQuantityByProduct = quantityByProductCodeBar
                .reduce(Integer::sum, Materialized.with(Serdes.String(), Serdes.Integer()))
                .filter((product, total) -> total > 0);

        totalQuantityByProduct.toStream().foreach((product, total) -> {
            String insertSql = "UPDATE product SET quantity = quantity + ? WHERE code_bar = ?";

            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                    stmt.setInt(1, total);
                    stmt.setString(2, product);
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                System.err.println("Error inserting record into database: " + e.getMessage());
            }
        });

    }

}
