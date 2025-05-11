package records;

import chess.ChessGame;

public class REPLData {
    public boolean replOne;
    public boolean replTwo;
    public boolean replThree;
    public boolean listQueried;
    public String authToken;
    public ChessGame.TeamColor color;
    public int gameID;

    public REPLData(boolean replOne, boolean replTwo, boolean replThree, boolean listQueried, String authToken, ChessGame.TeamColor color, int gameID) {
        this.replOne = replOne;
        this.replTwo = replTwo;
        this.replThree = replThree;
        this.listQueried = listQueried;
        this.authToken = authToken;
        this.color = color;
        this.gameID = gameID;
    }
}
