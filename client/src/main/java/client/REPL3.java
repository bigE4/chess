package client;

import engine.ChessGameEngine;
import facade.WebsocketFacade;
import records.REPLFlags;
import records.REPLToken;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class REPL3 implements NotificationHandler {

    public void replMain(Scanner scanner, REPLFlags flags, WebsocketFacade facade, REPLToken authToken) throws Exception {
        System.out.println("♕ Game Menu ♕");

        var menus = ClientUtils.initMenusThree();
        var helpMenu = menus.getFirst();

        ClientUtils.printMenu(helpMenu);

        // Initialize the game engine.
        // game engine stores mutable list of moves.
        // on LOAD_GAME, that list of moves is appended to
        // Localize runtime game logic to game engine.
        // When a client tries to make a move, the game engine
        // IS MY GAME ENGINE THE NOTIFICATION HANDLER?

        while (flags.replThree) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
                case "D", "d", "Draw", "draw" -> ChessGameEngine.printChessboard("WHITE");
                case "M", "m", "Move", "move" -> System.out.println("Moves!");
                case "R", "r", "Resign", "resign" -> System.out.println("Resigns!");
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
