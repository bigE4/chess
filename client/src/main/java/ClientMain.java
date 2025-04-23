import records.REPLFlags;
import records.REPLToken;
import client.REPL1;
import client.REPL2;
import client.REPL3;
import facade.ServerFacade;
import facade.WebsocketFacade;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false, false, false);
        REPLToken token = new REPLToken("");
        ServerFacade serverFacade = new ServerFacade("http://localhost:8080");

        REPL3 repl3 = new REPL3();
        WebsocketFacade websocketFacade = new WebsocketFacade();

        while (flags.replOne || flags.replTwo || flags.replThree) {
            if (flags.replOne) {
            REPL1.replMain(scanner, flags, token, serverFacade);
            }
            if (flags.replTwo) {
                REPL2.replMain(scanner, flags, serverFacade, token);
            }
            if (flags.replThree) {
                repl3.replMain(scanner, flags, websocketFacade, token);
            }
        }
    }
}