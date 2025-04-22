package dataaccess;

import dataaccess.interfaces.UserDAO;
import model.UserData;
import exceptions.DataAccessException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserSQLDAO implements UserDAO {

    @Override
    public boolean userExists(String username) throws DataAccessException {
        String query = "SELECT COUNT(*) FROM userData WHERE username = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, username);
            try (ResultSet results = prepared.executeQuery()) {
                if (results.next()) {
                    return results.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error checking if user exists: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void storeUser(UserData userData) throws DataAccessException {
        String query = "INSERT INTO userData (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            String hashedPassword = BCrypt.hashpw(userData.password(), BCrypt.gensalt());
            prepared.setString(1, userData.username());
            prepared.setString(2, hashedPassword);
            prepared.setString(3, userData.email());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error storing user: " + e.getMessage());
        }
    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        String query = "SELECT password FROM userData WHERE username = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, username);
            try (ResultSet results = prepared.executeQuery()) {
                if (results.next()) {
                    String hashedPassword = results.getString("password");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error authenticating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void clearUsers() throws DataAccessException {
        String query = "TRUNCATE TABLE userData";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing users: " + e.getMessage());
        }
    }
}
