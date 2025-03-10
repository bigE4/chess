package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameDatabaseDAO implements GameDAO {

    String gamePath = "src/main/java/dataaccess/exampledatabase/exGameDataBase.json";
    public List<GameData> gameDataList;

    public GameDatabaseDAO() {
        this.gameDataList = ExampleDatabaseReader.readListFromFile(gamePath, new TypeToken<List<GameData>>() {});
    }

    @Override
    public boolean gameExists(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void storeGame(GameData gameData) {
        if (!gameExists(gameData.gameID())) {
            gameDataList.add(gameData);
            ExampleDatabaseReader.writeListToFile(gamePath, gameDataList);
        }
    }

    @Override
    public void updateGame(GameData gameData) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameData.gameID())) {
                deleteGame(gameData.gameID());
                storeGame(gameData);
                ExampleDatabaseReader.writeListToFile(gamePath, gameDataList);
                return;
            }
        }
    }

    @Override
    public GameData retrieveGame(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public List<GameData> retrieveGames() {
        return gameDataList;
    }

    @Override
    public void deleteGame(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                gameDataList.remove(data);
                ExampleDatabaseReader.writeListToFile(gamePath, gameDataList);
                return;
            }
        }
    }

    @Override
    public void clearGames() {
        gameDataList = new ArrayList<>();
        ExampleDatabaseReader.writeListToFile(gamePath, gameDataList);
    }
}