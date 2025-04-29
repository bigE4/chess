package client;

import engine.ChessGameEngine;
import facade.WebsocketFacade;
import records.REPLData;
import websocket.messages.ServerMessage;

import java.util.List;
import java.util.Scanner;

public class REPL3 implements NotificationHandler {

    private Scanner scanner;
    private REPLData flags;
    private WebsocketFacade facade;
    private ChessGameEngine engine;

    public void replMain(Scanner scanner, REPLData flags, WebsocketFacade facade) throws Exception {
        System.out.println("♕ Game Menu ♕");

        this.engine = new ChessGameEngine(flags.color);
        this.scanner = scanner;
        this.flags = flags;
        this.facade = facade;

        var menus = ClientUtils.initMenusThree();
        var helpMenu = menus.get(0);
        var moveMenu = menus.get(1);

        engine.render();
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
                case "D", "d", "Draw", "draw" -> draw();
                case "M", "m", "Move", "move" -> move(moveMenu, scanner);
                case "R", "r", "Resign", "resign" -> resign();
                case "L", "l", "Legal", "legal" -> legal();
                case "Q", "q", "Quit", "quit" -> switchToREPL2(flags);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }
    }

    private void draw() {
        engine.render();
    }

    private void move(List<String> moveMenu, Scanner scanner) {
        List<String> responses = ClientUtils.queryMenu(moveMenu, scanner);
        System.out.println(responses);
    }

    private void resign() {
    }

    private void legal() {
    }

    public void switchToREPL2(REPLData flags) {
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
