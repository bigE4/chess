package dataaccess;

import chess.ChessGame;
import dataaccess.interfaces.GameDAO;
import exceptions.DataAccessException;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GameSQLDAO implements GameDAO {
    private static final Gson GSON = new Gson();
    @Override
    public boolean gameExists(int gameID) throws DataAccessException {
        String query = "SELECT COUNT(*) FROM gameData WHERE gameID = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, gameID);
            ResultSet results = prepared.executeQuery();
            if (results.next()) {
                return results.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error checking if game exists: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void storeGame(GameData gameData) throws DataAccessException {
        String query = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, gameData.gameID());
            prepared.setString(2, gameData.whiteUsername());
            prepared.setString(3, gameData.blackUsername());
            prepared.setString(4, gameData.gameName());
            String gameJson = GSON.toJson(gameData.game());
            prepared.setString(5, gameJson);
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error storing game: " + e.getMessage());
        }
    }

    @Override
    public void updatePlayerColors(GameData gameData) throws DataAccessException {
        String query = "UPDATE gameData SET whiteUsername = ?, blackUsername = ? WHERE gameID = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setString(1, gameData.whiteUsername());
            prepared.setString(2, gameData.blackUsername());
            prepared.setInt(3, gameData.gameID());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating player colors: " + e.getMessage());
        }
    }

    @Override
    public GameData retrieveGame(int gameID) throws DataAccessException {
        String query = "SELECT * FROM gameData WHERE gameID = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, gameID);
            ResultSet results = prepared.executeQuery();
            if (results.next()) {
                String gameJson = results.getString("game");
                ChessGame game = GSON.fromJson(gameJson, ChessGame.class);
                return new GameData(
                        results.getInt("gameID"),
                        results.getString("whiteUsername"),
                        results.getString("blackUsername"),
                        results.getString("gameName"),
                        game
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving game: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<GameData> retrieveGames() throws DataAccessException {
        List<GameData> games = new ArrayList<>();
        String query = "SELECT * FROM gameData";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query);
             ResultSet results = prepared.executeQuery()) {
            while (results.next()) {
                String gameJson = results.getString("game");
                ChessGame game = GSON.fromJson(gameJson, ChessGame.class);
                games.add(new GameData(
                        results.getInt("gameID"),
                        results.getString("whiteUsername"),
                        results.getString("blackUsername"),
                        results.getString("gameName"),
                        game
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving games: " + e.getMessage());
        }
        return games;
    }

    @Override
    public void deleteGame(int gameID) throws DataAccessException {
        String query = "DELETE FROM gameData WHERE gameID = ?";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, gameID);
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error Clearing gameData: " + e.getMessage());
        }
    }

    @Override
    public void clearGames() throws DataAccessException {
        String query = "TRUNCATE TABLE gameData";
        try (Connection connection = SQLDatabaseManager.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error Clearing gameData: " + e.getMessage());
        }
    }
}
