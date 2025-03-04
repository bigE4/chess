import chess.ChessGame;
import dataaccess.ex.exDBReader;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        String userPath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exUserDataBase.json";
        String authPath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exAuthDataBase.json";
        String gamePath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exGameDataBase.json";

        List<UserData> listOfUsers = new ArrayList<>(Arrays.asList(
                new UserData("Ian", "bubbles", "ianjellsworth@gmail.com"),
                new UserData("Vivian", "BUBBLES", "vivhmells@yahoo.com")
        ));

        List<AuthData> listOfAuths = new ArrayList<>(Arrays.asList(
                new AuthData("6568038", "bubbles"),
                new AuthData("4205778", "BUBBLES")
        ));

        List<GameData> listOfGames = new ArrayList<>(List.of(
                new GameData(8038, "Taymyth", "Taylor", "TT", new ChessGame())
        ));

        exDBReader.writeListToFile(userPath, listOfUsers);
        exDBReader.writeListToFile(authPath, listOfAuths);
        exDBReader.writeListToFile(gamePath, listOfGames);

        System.out.println("Complete");
    }
}
