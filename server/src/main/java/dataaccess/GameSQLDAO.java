package dataaccess;

import model.GameData;

import java.util.List;

public class GameSQLDAO implements GameDAO{
    @Override
    public boolean gameExists(int gameID) {
        return false;
    }

    @Override
    public void storeGame(GameData gameData) {

    }

    @Override
    public void updateGame(GameData gameData) {

    }

    @Override
    public GameData retrieveGame(int gameID) {
        return null;
    }

    @Override
    public List<GameData> retrieveGames() {
        return List.of();
    }

    @Override
    public void deleteGame(int gameID) {

    }

    @Override
    public void clearGames() {

    }
}
