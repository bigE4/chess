package records;

import chess.ChessGame;

public class REPLData {
    public boolean replOne;
    public boolean replTwo;
    public boolean replThree;
    public boolean listQueried;
    public String authToken;
    public ChessGame.TeamColor color;

    public REPLData(boolean replOne, boolean replTwo, boolean replThree, boolean listQueried, String authToken, ChessGame.TeamColor color) {
        this.replOne = replOne;
        this.replTwo = replTwo;
        this.replThree = replThree;
        this.listQueried = listQueried;
        this.authToken = authToken;
        this.color = color;
    }
}
