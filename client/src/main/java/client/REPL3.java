package client;

import facade.WebsocketFacade;
import records.REPLFlags;
import records.REPLToken;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class REPL3 {

    public void replMain(Scanner scanner, REPLFlags flags, WebsocketFacade facade, REPLToken authToken) throws Exception {
        System.out.println("♕ REPL3 ACCESSED! ♕");
        while (flags.replThree) {
            String response = scanner.nextLine();
            switch (response) {
                case "Q", "q", "Quit", "quit" -> switchToREPL2(flags);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }
    }

    public void switchToREPL2(REPLFlags flags) {
        flags.replTwo = true;
        flags.replThree = false;
    }

    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()) {
            case ERROR: {

            }
            case LOAD_GAME: {

            }
            case NOTIFICATION: {

            }
        }
    }
}
