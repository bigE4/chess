package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public interface GameDAO {
    boolean GameExists(String gameName);
    boolean StoreGame(GameData gameData);
    boolean UpdateGame(GameData gameData);
    GameData RetrieveGame(int gameID);
    List<GameData> RetrieveGames();
    boolean DeleteGame(int gameID);
    void ClearGames();
}
