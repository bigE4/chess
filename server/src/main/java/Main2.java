import chess.ChessGame;
import dataaccess.ExampleDatabaseReader;
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
                new AuthData("692c2a83-88d9-4dcd-b9c8-c96a5570bd44", "Ian"),
                new AuthData("a4b8e505-dc99-40d2-8f77-862226226e27", "Vivian")
        ));

        List<GameData> listOfGames = new ArrayList<>(List.of(
                new GameData(8038, "Ian", "Vivian", "Ells", new ChessGame())
        ));

        ExampleDatabaseReader.writeListToFile(userPath, listOfUsers);
        ExampleDatabaseReader.writeListToFile(authPath, listOfAuths);
        ExampleDatabaseReader.writeListToFile(gamePath, listOfGames);

        System.out.println("Complete");
    }
}
