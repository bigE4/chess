package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public interface GameDAO {
    boolean isGame(String gameName);
    boolean storeGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game);
    boolean updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game);
    GameData retrieveGame(String gameName);
    List<GameData> retrieveGames();
    boolean deleteGame(int gameID);
    void clearGames();
}
