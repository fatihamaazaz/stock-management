package stock.management.sales_service.components;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RequiredArgsConstructor
@Component
public class DatabaseService {

    private final Connection connection;

    public boolean checkUserExitence(String username, String password) {
        String selectSql = "SELECT * FROM employee WHERE username = ? and password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

}
