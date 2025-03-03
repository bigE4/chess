package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public class GameDatabaseDAO implements GameDAO {
    @Override
    public boolean isGame(String gameName) {
        return false;
    }

    @Override
    public boolean storeGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        return false;
    }

    @Override
    public boolean updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        return false;
    }

    @Override
    public GameData retrieveGame(String gameName) {
        return null;
    }

    @Override
    public List<GameData> retrieveGames() {
        return List.of();
    }

    @Override
    public boolean deleteGame(int gameID) {
        return false;
    }

    @Override
    public void clearGames() {

    }
}
