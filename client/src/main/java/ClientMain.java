import chess.ChessGame;
import records.REPLData;
import client.REPL1;
import client.REPL2;
import client.REPL3;
import facade.ServerFacade;
import facade.WebsocketFacade;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLData flags = new REPLData(true, false, false, false, "", ChessGame.TeamColor.WHITE, 0);
        ServerFacade sFacade = new ServerFacade("http://localhost:8080");

        REPL3 repl3 = new REPL3();
        WebsocketFacade wsFacade = new WebsocketFacade();

        while (flags.replOne || flags.replTwo || flags.replThree) {
            if (flags.replOne) {
            REPL1.replMain(scanner, flags, sFacade);
            }
            if (flags.replTwo) {
                REPL2.replMain(scanner, flags, sFacade);
            }
            if (flags.replThree) {
                repl3.replMain(scanner, flags, wsFacade);
            }
        }
    }
}