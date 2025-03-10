package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {
    boolean gameExists(int gameID);
    void storeGame(GameData gameData);
    void updateGame(GameData gameData);
    GameData retrieveGame(int gameID);
    List<GameData> retrieveGames();
    void deleteGame(int gameID);
    void clearGames();
}
