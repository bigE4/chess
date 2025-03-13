package dataaccess;

import exceptions.DataAccessException;
import model.GameData;

import java.util.List;

public interface GameDAO {
    boolean gameExists(int gameID) throws DataAccessException;
    void storeGame(GameData gameData) throws DataAccessException;
    void updateGame(GameData gameData) throws DataAccessException;
    GameData retrieveGame(int gameID) throws DataAccessException;
    List<GameData> retrieveGames() throws DataAccessException;
    void deleteGame(int gameID) throws DataAccessException;
    void clearGames() throws DataAccessException;
}
