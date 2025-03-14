package dataaccess;

import exceptions.DataAccessException;
import model.GameData;

import java.sql.SQLException;
import java.util.List;

public interface GameDAO {
    boolean gameExists(int gameID) throws DataAccessException;
    void storeGame(GameData gameData) throws DataAccessException;
    void updatePlayerColors(GameData gameData) throws DataAccessException;
    GameData retrieveGame(int gameID) throws DataAccessException;
    List<GameData> retrieveGames() throws DataAccessException;
    void deleteGame(int gameID) throws DataAccessException;
    void clearGames() throws DataAccessException, SQLException;
}
