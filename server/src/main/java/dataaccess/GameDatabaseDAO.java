package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameDatabaseDAO implements GameDAO {

    String gamePath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exGameDataBase.json";
    public List<GameData> gameDataList;

    public GameDatabaseDAO(List<GameData> gameDataList) {
        this.gameDataList = gameDataList;
    }

    public GameDatabaseDAO() {
        this.gameDataList = exDBReader.readListFromFile(gamePath, new TypeToken<List<GameData>>() {});
    }

    @Override
    public boolean GameExists(String gameName) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameName(), gameName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean GameExists(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void StoreGame(GameData gameData) {
        if (!GameExists(gameData.gameName())) {
            gameDataList.add(gameData);
            exDBReader.writeListToFile(gamePath, gameDataList);
        }
    }

    @Override
    public boolean UpdateGame(GameData gameData) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameData.gameID())) {
                DeleteGame(gameData.gameID());
                StoreGame(gameData);
                exDBReader.writeListToFile(gamePath, gameDataList);
                return true;
            }
        }
        return false;
    }

    @Override
    public GameData RetrieveGame(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public List<GameData> RetrieveGames() {
        return gameDataList;
    }

    @Override
    public void DeleteGame(int gameID) {
        for (GameData data: gameDataList) {
            if (Objects.equals(data.gameID(), gameID)) {
                gameDataList.remove(data);
                exDBReader.writeListToFile(gamePath, gameDataList);
                return;
            }
        }
    }

    @Override
    public void ClearGames() {
        gameDataList = new ArrayList<>();
        exDBReader.writeListToFile(gamePath, gameDataList);
    }
}