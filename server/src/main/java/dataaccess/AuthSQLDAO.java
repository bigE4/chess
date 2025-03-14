package dataaccess;

import model.AuthData;
import exceptions.DataAccessException;
import java.sql.*;

public class AuthSQLDAO implements AuthDAO {
    @Override
    public void storeAuth(AuthData authData) throws DataAccessException {
        String query = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, authData.authToken());
            prepared.setString(2, authData.username());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error storing auth data: " + e.getMessage());
        }
    }

    @Override
    public boolean authenticateAuth(String authToken) throws DataAccessException {
        String query = "SELECT COUNT(*) FROM authData WHERE authToken = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, authToken);
            try (ResultSet results = prepared.executeQuery()) {
                if (results.next()) {
                    return results.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error authenticating authToken: " + e.getMessage());
        }
        return false;
    }

    @Override
    public AuthData retrieveAuth(String authToken) throws DataAccessException {
        String query = "SELECT * FROM authData WHERE authToken = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, authToken);
            try (ResultSet results = prepared.executeQuery()) {
                if (results.next()) {
                    String username = results.getString("username");
                    return new AuthData(authToken, username);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving auth data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        String query = "DELETE FROM authData WHERE authToken = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, authToken);
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting auth data: " + e.getMessage());
        }
    }

    @Override
    public void clearAuth() throws DataAccessException {
        String query = "TRUNCATE TABLE authData";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing authData: " + e.getMessage());
        }
    }
}
