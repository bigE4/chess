package dataaccess;

import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    public static void testConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("Connection successful!");
        } catch (DataAccessException | SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testConnection();
    }
}
