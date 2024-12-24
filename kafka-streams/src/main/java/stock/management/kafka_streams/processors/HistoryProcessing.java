package stock.management.kafka_streams.processors;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import stock.management.kafka_streams.entities.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Component
public class HistoryProcessing {

    @Value(value = "${spring.datasource.username}")
    private String dbUser;

    @Value(value = "${spring.datasource.password}")
    private String dbPassword;

    @Value(value = "${spring.datasource.url}history")
    private String dbUrl;

    public void process(KStream<String, Event> stream){
        stream.foreach((key, value) -> {

            String insertSql = "INSERT INTO events (employee, product, quantity, eventType) VALUES (?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                    stmt.setString(1, value.getEmployee());
                    stmt.setString(2, value.getProduct());
                    stmt.setInt(3, value.getQuantity());
                    stmt.setString(4, value.getEventType());
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                System.err.println("Error inserting record into database: " + e.getMessage());
            }
        });
    }
}
