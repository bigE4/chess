package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {
    boolean GameExists(String gameName);
    boolean GameExists(int gameID);
    void StoreGame(GameData gameData);
    boolean UpdateGame(GameData gameData);
    GameData RetrieveGame(int gameID);
    List<GameData> RetrieveGames();
    void DeleteGame(int gameID);
    void ClearGames();
}
