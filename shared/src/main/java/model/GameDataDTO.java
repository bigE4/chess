package model;

public record GameDataDTO(int gameID, String whiteUsername, String blackUsername, String gameName) {
    public GameDataDTO(GameData gameData) {
        this(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName());
    }
}
